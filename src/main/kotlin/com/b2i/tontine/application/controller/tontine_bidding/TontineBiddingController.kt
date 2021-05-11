package com.b2i.tontine.application.controller.tontine_bidding

import com.b2i.tontine.application.controlForm.Color
import com.b2i.tontine.application.controlForm.ControlForm
import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.entity.UserType
import com.b2i.tontine.domain.account.member.port.MemberDomain
import com.b2i.tontine.domain.account.port.UserDomain
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.association.port.AssociationDomain
import com.b2i.tontine.domain.association_member.port.AssociationMemberDomain
import com.b2i.tontine.domain.tontine.port.TontineDomain
import com.b2i.tontine.domain.tontine_bidding.entity.TontineBidding
import com.b2i.tontine.domain.tontine_bidding.port.TontineBiddingDomain
import com.b2i.tontine.domain.tontine_periodicity.port.TontinePeriodicityDomain
import com.b2i.tontine.utils.OperationResult
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/05/06
 * @Time: 19:58
 */
@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_ASSOCIATION])
class TontineBiddingController(
    private val tontineBiddingDomain: TontineBiddingDomain,
    private val tontinePeriodicityDomain: TontinePeriodicityDomain,
    private val tontineDomain: TontineDomain,
    private val associationDomain: AssociationDomain,
    private val associationMemberDomain: AssociationMemberDomain,
    private val memberDomain: MemberDomain,
    private val authenticationFacade: AuthenticationFacade,
    private val userDomain: UserDomain,
    private val messageSource: MessageSource
) : BaseController("/backend/tontine/", ControllerEndpoint.BACKEND_ASSOCIATION) {

    @PostMapping(value = ["/{association_id}/tontines/{tontine_id}/periodicity/bidding"])
    fun memberMakingBidding(
        redirectAttributes: RedirectAttributes,
        @PathVariable association_id: String,
        @PathVariable tontine_id: String,
        @RequestParam id: String,
        @RequestParam interestType: String,
        @RequestParam interest: String,
        locale: Locale
    ): String {
        var url = "$association_id/tontines/$tontine_id"

        when {
            association_id.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("association_member_id_association_empty", null, locale),
                    Color.red
                )
            }
            tontine_id.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontine_id_empty", null, locale),
                    Color.red
                )
            }
            else -> {
                val user = authenticationFacade.getAuthenticatedUser().get()
                val tontine = tontineDomain.findTontineById(tontine_id.toLong()).orElse(null)

                when (userDomain.findTypeBy(user.id)) {
                    UserType.ASSOCIATION_MEMBER -> {
                        var interestValue = 0.0
                        val tontineBidding = TontineBidding()

                        if (interestType == "percent") {
                            interestValue = ((tontine.tontineGlobalAmount * interest.toInt()) / 100)
                            tontineBidding.interestByPercentage = interest.toInt()
                        } else if (interestType == "value") {
                            interestValue = interest.toDouble()
                        }
                        tontineBidding.interestByValue = interestValue

                        val memb = memberDomain.findById(user.id).orElse(null)
                        if (memb != null) {
                            tontineBidding.member = memb
                        }

                        val result: OperationResult<TontineBidding> =
                            tontineBiddingDomain.makeBidding(tontineBidding, id.toLong())

                        val err: MutableMap<String, String> = mutableMapOf()
                        if (result.errors!!.isNotEmpty()) {
                            result.errors.forEach { (key, value) ->
                                err[key] = messageSource.getMessage(value, null, locale)
                            }
                        }

                        if (
                            ControlForm.verifyHashMapRedirect(
                                redirectAttributes,
                                err,
                                messageSource.getMessage("tontine_bidding_created", null, locale)
                            )
                        ) {
                            url = "$association_id/tontines/$tontine_id/periodicity/$id"
                        }
                    }
                }
            }
        }

        return redirectTo(url)
    }

    @PostMapping(value = ["/{association_id}/tontines/{tontine_id}/periodicity/{periodicity_id}/bidding/{bidding_id}"])
    fun validateBidding(
        redirectAttributes: RedirectAttributes,
        @PathVariable association_id: String,
        @PathVariable tontine_id: String,
        @PathVariable periodicity_id: String,
        @PathVariable bidding_id: String,
        locale: Locale
    ): String {
        var url = "$association_id/tontines/$tontine_id"

        when {
            association_id.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("association_member_id_association_empty", null, locale),
                    Color.red
                )
            }
            tontine_id.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontine_id_empty", null, locale),
                    Color.red
                )
            }
            bidding_id.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontine_bidding_not_found", null, locale),
                    Color.red
                )
            }
            else -> {
                val result: OperationResult<TontineBidding> = tontineBiddingDomain.validateBidding(bidding_id.toLong())
                val err: MutableMap<String, String> = mutableMapOf()
                if (result.errors!!.isNotEmpty()) {
                    result.errors.forEach { (key, value) ->
                        err[key] = messageSource.getMessage(value, null, locale)
                    }
                }

                if (
                    ControlForm.verifyHashMapRedirect(
                        redirectAttributes,
                        err,
                        messageSource.getMessage("tontine_bidding_validate", null, locale)
                    )
                ) {
                    url = "$association_id/tontines/$tontine_id/periodicity/$periodicity_id"
                }
            }
        }

        return redirectTo(url)
    }

    @GetMapping("/{association_id}/tontines/{tontine_id}/periodicity/{periodicity_id}/bidding")
    fun listOfPeriodicityBidding(
        model: Model,
        @PathVariable association_id: String,
        @PathVariable tontine_id: String,
        @PathVariable periodicity_id: String,
        locale: Locale
    ): String {

        when {
            association_id.isEmpty() -> {
                ControlForm.model(
                    model,
                    messageSource.getMessage("association_member_id_association_empty", null, locale),
                    Color.red
                )
            }
            tontine_id.isEmpty() -> {
                ControlForm.model(
                    model,
                    messageSource.getMessage("tontine_id_empty", null, locale),
                    Color.red
                )
            }
            else -> {
                val user = authenticationFacade.getAuthenticatedUser().get()
                when (userDomain.findTypeBy(user.id)) {
                    UserType.ACTUATOR -> {
                        val tontinePeriodicity = tontinePeriodicityDomain.findById(periodicity_id.toLong()).orElse(null)
                        if (tontinePeriodicity != null) {
                            val biddingS = tontineBiddingDomain.findAllBiddingByPeriodicity(tontinePeriodicity)
                            model.addAttribute("tontine_bidding", biddingS)
                            injectConnectedUser(model, association_id)
                        }
                    }
                    UserType.ASSOCIATION_MEMBER -> {
                        val association = associationDomain.findAssociationById(association_id.toLong()).orElse(null)
                        if (association != null) {
                            val member = associationMemberDomain.findByAssociationAndUser(association, user)
                            if (member.isPresent) {
                                val connectedUser = member.get().role

                                if (connectedUser == "PDG") {
                                    val tontinePeriodicity = tontinePeriodicityDomain.findById(periodicity_id.toLong()).orElse(null)
                                    if (tontinePeriodicity != null) {
                                        val biddingS = tontineBiddingDomain.findAllBiddingByPeriodicity(tontinePeriodicity)
                                        model.addAttribute("tontine_bidding", biddingS)
                                        injectConnectedUser(model, association_id)
                                    }
                                } else {
                                    val tontinePeriodicity = tontinePeriodicityDomain.findById(periodicity_id.toLong()).orElse(null)
                                    val memb = memberDomain.findById(user.id).orElse(null)
                                    if (tontinePeriodicity != null && memb != null) {
                                        val biddingS = tontineBiddingDomain.findAllBiddingByPeriodicityAndMember(tontinePeriodicity, memb)
                                        model.addAttribute("tontine_bidding", biddingS)
                                        injectConnectedUser(model, association_id)
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        return forwardTo("list_tontine")
    }

    //Inject association in view
    fun injectConnectedUser(model: Model, association_id: String) {
        val association: Association? = associationDomain.findAssociationById(association_id.toLong()).orElse(null)
        val user = authenticationFacade.getAuthenticatedUser().get()
        var connectedUser = "actuator"

        if (association != null) {
            model.addAttribute("association", association)
            model.addAttribute("connectedUserId", user.id)

            when (userDomain.findTypeBy(user.id)) {
                UserType.ASSOCIATION_MEMBER -> {
                    val member = associationMemberDomain.findByAssociationAndUser(association, user)
                    if (member.isPresent) {
                        connectedUser = member.get().role
                        model.addAttribute("connectedUser", connectedUser)
                    }
                }
                UserType.ACTUATOR -> {
                    model.addAttribute("connectedUser", connectedUser)
                }
            }
        }
    }

}