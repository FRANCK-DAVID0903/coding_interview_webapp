package com.b2i.tontine.application.controller.tontine

import com.b2i.tontine.application.controlForm.Color
import com.b2i.tontine.application.controlForm.ControlForm
import org.springframework.stereotype.Controller
import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.association.port.AssociationDomain
import com.b2i.tontine.domain.association_member.entity.AssociationMember
import com.b2i.tontine.domain.association_member.port.AssociationMemberDomain
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine.port.TontineDomain
import com.b2i.tontine.domain.tontine_request.port.TontineRequestDomain
import com.b2i.tontine.utils.OperationResult
import org.springframework.context.MessageSource
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.time.LocalDate
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/20
 * @Time: 22:03
 */
@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_ASSOCIATION])
class TontineController(
    private val tontineDomain: TontineDomain,
    private val associationDomain: AssociationDomain,
    private val associationMemberDomain: AssociationMemberDomain,
    private val tontineRequestDomain: TontineRequestDomain,
    private val messageSource: MessageSource
) :
    BaseController("/backend/tontine/", ControllerEndpoint.BACKEND_ASSOCIATION) {

    @GetMapping("/{association_id}/tontines/create")
    fun goToCreateTontine(model: Model, @PathVariable association_id: String): String {
        injectAssociation(model, association_id)

        return forwardTo("add_tontine")
    }

    @PostMapping("/{association_id}/tontines/create")
    fun createTontine(
        redirectAttributes: RedirectAttributes,
        @PathVariable association_id: String,
        @RequestParam name: String,
        @RequestParam type: String,
        @RequestParam numberOfParticipant: String,
        @RequestParam contributionAmount: String,
        @RequestParam startDate: String,
        @RequestParam endDate: String,
        @RequestParam periodicity: String,
        @RequestParam membershipDeadline: String,
        locale: Locale
    ): String {
        var url = "$association_id/tontines/create"

        when {
            association_id.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("association_member_id_association_empty", null, locale),
                    Color.red
                )
            }
            numberOfParticipant.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontine_participant_empty", null, locale),
                    Color.red
                )
            }
            contributionAmount.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontine_contribution_amount_empty", null, locale),
                    Color.red
                )
            }
            startDate.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontine_contribution_startDate_empty", null, locale),
                    Color.red
                )
            }
            endDate.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontine_contribution_endDate_empty", null, locale),
                    Color.red
                )
            }
            else -> {
                val tontine = Tontine()
                tontine.name = name
                tontine.type = objectHelper.getTontineType(type)
                tontine.periodicity = objectHelper.getTontinePeriodicity(periodicity)
                tontine.numberOfParticipantEstimated = numberOfParticipant.toLong()
                tontine.contributionAmount = contributionAmount.toDouble()
                tontine.startDate = ControlForm.formatDate(startDate)
                tontine.endDate = ControlForm.formatDate(endDate)
                tontine.membershipDeadline = ControlForm.formatDate(membershipDeadline)

                val result: OperationResult<Tontine> = tontineDomain.createTontine(tontine, association_id.toLong())

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
                        messageSource.getMessage("tontine_save_success", null, locale)
                    )
                ) {
                    url = "$association_id/tontines"
                }

            }
        }

        return redirectTo(url)
    }

    @PostMapping(value = ["/{association_id}/tontines/{tontine_id}/extendMembership"])
    fun extendTontineMembership(
        redirectAttributes: RedirectAttributes,
        @PathVariable association_id: String,
        @PathVariable tontine_id: String,
        @RequestParam membershipDeadline: String,
        locale: Locale
    ): String {
        var url = "$association_id/tontines"

        when {
            membershipDeadline.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontine_membership_deadline", null, locale),
                    Color.red
                )
            }
            else -> {
                val result: OperationResult<Tontine> =
                    tontineDomain.extendTontineMembershipDeadline(tontine_id.toLong(), membershipDeadline)

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
                        messageSource.getMessage("tontine_save_success", null, locale)
                    )
                ) {
                    url = "$association_id/tontines/$tontine_id"
                }
            }
        }
        return redirectTo(url)
    }

    @PostMapping(value = ["/{association_id}/tontines/{tontine_id}/closeMembership"])
    fun closeTontineMembership(
        redirectAttributes: RedirectAttributes,
        @PathVariable association_id: String,
        @PathVariable tontine_id: String,
        locale: Locale
    ): String {
        var url = "$association_id/tontines"

        val result: OperationResult<Tontine> =
            tontineDomain.closeTontineMembership(tontine_id.toLong())

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
                messageSource.getMessage("tontine_save_success", null, locale)
            )
        ) {
            url = "$association_id/tontines/$tontine_id"
        }

        return redirectTo(url)
    }

    @GetMapping("/{association_id}/tontines")
    fun listOfAssociationTontines(
        model: Model, @PathVariable association_id: String,
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
            else -> {
                val association = associationDomain.findAssociationById(association_id.toLong())

                if (association.isPresent) {
                    val tontines = tontineDomain.findAllTontinesByAssociation(association.get())
                    model.addAttribute("association_tontines", tontines)
                }
            }
        }

        injectAssociation(model, association_id)

        return forwardTo("list_tontine")
    }

    @GetMapping(value = ["/{association_id}/tontines/{tontine_id}"])
    fun tontineDetail(model: Model, @PathVariable tontine_id: String, @PathVariable association_id: String): String {
        var page = "detail_tontine"
        var isMembershipDeadline = false
        val association: Association? = associationDomain.findAssociationById(association_id.toLong()).orElse(null)
        val tontine: Tontine? = tontineDomain.findTontineById(tontine_id.toLong()).orElse(null)

        if (association == null || tontine == null) {
            page = "list_tontine"
        } else {
            val associationMembers: List<AssociationMember> =
                associationMemberDomain.findAllMembersInAssociation(association)

            val today = ControlForm.formatDate(LocalDate.now().toString())
            if (tontine.membershipDeadline!!.before(today)) {
                isMembershipDeadline = true
            }

            model.addAttribute("associationMembers", associationMembers)
            model.addAttribute("tontineRequests", tontineRequestDomain.findAllApprovedTontineMembers(tontine, false, 0))
            model.addAttribute("tontineMembers", tontineRequestDomain.findAllApprovedTontineMembers(tontine, true, 0))
            model.addAttribute("association", association)
            model.addAttribute("membershipDeadline", isMembershipDeadline)
            model.addAttribute("tontine", tontine)
        }

        return forwardTo(page)
    }

    //Inject association in view
    fun injectAssociation(model: Model, id: String) {
        val association: Association? = associationDomain.findAssociationById(id.toLong()).orElse(null)
        if (association != null) {
            model.addAttribute("association", association)
        }
    }

}