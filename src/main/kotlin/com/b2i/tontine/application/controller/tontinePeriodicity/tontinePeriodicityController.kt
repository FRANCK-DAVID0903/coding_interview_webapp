package com.b2i.tontine.application.controller.tontinePeriodicity

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
import com.b2i.tontine.domain.notification.entity.Notification
import com.b2i.tontine.domain.notification.port.NotificationDomain
import com.b2i.tontine.domain.tontine.entity.TontineType
import com.b2i.tontine.domain.tontine.port.TontineDomain
import com.b2i.tontine.domain.tontine_bidding.port.TontineBiddingDomain
import com.b2i.tontine.domain.tontine_contribution.entity.TontineContribution
import com.b2i.tontine.domain.tontine_contribution.port.TontineContributionDomain
import com.b2i.tontine.domain.tontine_periodicity.port.TontinePeriodicityDomain
import com.b2i.tontine.domain.tontine_request.port.TontineRequestDomain
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.text.SimpleDateFormat
import java.util.*

@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_PERIODICITY])
class tontinePeriodicityController(
        private val authenticationFacade: AuthenticationFacade,
        private val tontinePeriodicityDomain: TontinePeriodicityDomain,
        private val userDomain: UserDomain,
        private val memberDomain: MemberDomain,
        private val associationDomain: AssociationDomain,
        private val associationMemberDomain: AssociationMemberDomain,
        private val tontineRequestDomain: TontineRequestDomain,
        private val tontineContributionDomain: TontineContributionDomain,
        private val tontineBiddingDomain: TontineBiddingDomain,
        private val tontineDomain: TontineDomain,
        private val messageSource: MessageSource,
        private val notificationDomain: NotificationDomain

): BaseController(ControllerEndpoint.BACKEND_PERIODICITY) {

    @GetMapping("/details/{id}")
    fun goToListDetail(
            model: Model,
            @PathVariable id: String,
    ): String {

        val tontinePeriodicity = tontinePeriodicityDomain.findById(id.toLong()).orElse(null)

        if (tontinePeriodicity != null){
            val asso = tontinePeriodicity.tontine!!.association

            model.addAttribute("tontinePeriodicityMembers", tontineContributionDomain.findAllByTontinePeriodicity(tontinePeriodicity))
            model.addAttribute("tontinePeriodicityBidding", tontineBiddingDomain.findAllBiddingByPeriodicity(tontinePeriodicity))
            model.addAttribute("periodicity", tontinePeriodicity)
            model.addAttribute("periodicity_id",id)

            if (asso != null) {
                injectAssociation(model, asso)
            }
        }

        return forwardTo("list_contributions")
    }

    @PostMapping(value = ["/details/{periodicity_id}/contribute"])
    fun addContribution(
            redirectAttributes: RedirectAttributes,
            @PathVariable periodicity_id: String,
            @RequestParam id: String,
            @RequestParam payementMethod: String,
            @RequestParam contributionDate: String,
            @RequestParam forMe: String,
            locale: Locale
    ): String {
        var url = "periodicity/details/$periodicity_id"


        //get tontinePeriodicity by id
        val periodicity = tontinePeriodicityDomain.findById(periodicity_id.toLong()).orElse(null)
        val member = memberDomain.findById(id.toLong()).get()
        val tontine = tontineDomain.findTontineById(periodicity.tontine!!.id)

        if (periodicity != null){

            val contribution = tontineContributionDomain.findByTontinePeriodicityAndMemberAndTontine(periodicity,member,tontine.get()).orElse(null)

            if (contribution != null){
                if (forMe.toInt() == 1){
                    contribution.state = 1
                }else{
                    contribution.state = 0
                }
                contribution.contributed = true
                contribution.contributionAmount = periodicity.tontine!!.contributionAmount
                contribution.contributionDate = SimpleDateFormat("yyyy-MM-dd").parse(contributionDate)
                contribution.paymentMethod = payementMethod

                val result = tontineContributionDomain.saveContribution(contribution)

                val err: MutableMap<String, String> = mutableMapOf()
                if (result.errors!!.isNotEmpty()) {
                    result.errors.forEach {
                        (key, value) -> err[key] = messageSource.getMessage(value, null, locale)
                    }
                }

                if (
                        ControlForm.verifyHashMapRedirect(
                                redirectAttributes,
                                err,
                                messageSource.getMessage("periodicity_contribution_save_success", null, locale)
                        )
                ) {
                    url = "details/$periodicity_id"
                }
            }


        }
        return redirectTo(url)
    }

    @PostMapping(value = ["/details/{periodicity_id}/close"])
    fun closePeriodicity(
            redirectAttributes: RedirectAttributes,
            @PathVariable periodicity_id: String,
            @RequestParam id: String,
            @RequestParam deadlineDate: String,
            @RequestParam biddingAmount: String,
            @RequestParam type: String,
            locale: Locale,
            model: Model
    ): String {
        var url = "periodicity/details/$periodicity_id"

        //get tontinePeriodicity by id
        val periodicity = tontinePeriodicityDomain.findById(periodicity_id.toLong()).orElse(null)

        if (periodicity != null){

            val tontine = periodicity.tontine

            var listMember = tontine?.let { tontineRequestDomain.findAllApprovedTontineMembers(it, true, 0) }

            periodicity.biddingState = TontineType.OPENED
            periodicity.biddingDeadline = SimpleDateFormat("yyyy-MM-dd").parse(deadlineDate)
            periodicity.nextPeriodicity = false



            if(type == "EN VALEUR"){
                periodicity.biddingAmount = biddingAmount.toDouble()
            }else{
                periodicity.biddingAmount = (periodicity.tontine!!.contributionAmount*periodicity.tontine!!.numberOfParticipant) * (biddingAmount.toDouble()/100)
            }

            //On trouve la prochaine periodicité pour la mettre a jour
            val nextP = tontinePeriodicityDomain.findByPeriodicityNumberAndTontine((periodicity.periodicityNumber+1),periodicity.tontine!!).orElse(null)
            if (nextP != null){
                //newCodeAdd
                nextP.periodicityState = TontineType.OPENED

                nextP.nextPeriodicity = true
            }
            //Fin de la mise a jour de la prochaine périodicité

            val result = tontinePeriodicityDomain.saveTontinePeriodicity(periodicity)

            val err: MutableMap<String, String> = mutableMapOf()
            if (result.errors!!.isNotEmpty()) {
                result.errors.forEach {
                    (key, value) -> err[key] = messageSource.getMessage(value, null, locale)
                }
            }

            if (
                    ControlForm.verifyHashMapRedirect(
                            redirectAttributes,
                            err,
                            messageSource.getMessage("periodicity_tontine_contribution_close_done", null, locale)
                    )
            ) {
                listMember?.forEach{ member ->

                    val memberFinder = memberDomain.findById(member.beneficiary!!.id).get()
                    val notification = Notification()

                    notification.tontine = tontine
                    notification.user = member.beneficiary
                    notification.periodicity = periodicity
                    notification.association = tontine!!.association

                    notificationDomain.saveNotification(notification)
                }

                url = "details/$periodicity_id"


            }

        }
        return redirectTo(url)
    }

    @PostMapping(value = ["/details/{periodicity_id}/approveOffer"])
    fun approveOffer(
            redirectAttributes: RedirectAttributes,
            @PathVariable periodicity_id: String,
            @RequestParam id: String,
            locale: Locale,
            model: Model
    ): String {
        var url = "periodicity/details/$periodicity_id"

        //get tontinePeriodicity by id
        val periodicity = tontinePeriodicityDomain.findById(periodicity_id.toLong()).orElse(null)

        //get bidding selected by id
        val bidding = tontineBiddingDomain.findBiddingById(id.toLong()).orElse(null)
        val tontine = periodicity.tontine

        var listMember = tontine?.let { tontineRequestDomain.findAllApprovedTontineMembers(it, true, 0) }


        if (bidding != null){

                val result = tontineBiddingDomain.apprroveBidding(bidding)

                val err: MutableMap<String, String> = mutableMapOf()
                if (result.errors!!.isNotEmpty()) {
                    result.errors.forEach {
                        (key, value) -> err[key] = messageSource.getMessage(value, null, locale)
                    }
                }

                if (
                        ControlForm.verifyHashMapRedirect(
                                redirectAttributes,
                                err,
                                messageSource.getMessage("periodicity_tontine_approve_interest_member_offer_done", null, locale)
                        )
                ) {

                    listMember?.forEach{ member ->

                        val memberFinder = memberDomain.findById(member.beneficiary!!.id).get()
                        val notification = Notification()

                        notification.tontine = tontine
                        notification.user = member.beneficiary
                        notification.periodicity = periodicity
                        notification.association = tontine!!.association

                        notificationDomain.saveNotification(notification)
                    }

                    url = "details/$periodicity_id"

                }


        }
        return redirectTo(url)
    }

    @PostMapping(value = ["/details/{periodicity_id}/validateOffer"])
    fun validateOffer(
            redirectAttributes: RedirectAttributes,
            @PathVariable periodicity_id: String,
            @RequestParam id: String,
            locale: Locale
    ): String {
        var url = "periodicity/details/$periodicity_id"

        //get tontinePeriodicity by id
        val periodicity = tontinePeriodicityDomain.findById(periodicity_id.toLong()).orElse(null)

        //get bidding selected by id
        val bidding = tontineBiddingDomain.findBiddingById(id.toLong()).orElse(null)

        if (bidding != null){

            val result = tontineBiddingDomain.validateBidding(bidding)

            val err: MutableMap<String, String> = mutableMapOf()
            if (result.errors!!.isNotEmpty()) {
                result.errors.forEach {
                    (key, value) -> err[key] = messageSource.getMessage(value, null, locale)
                }
            }

            if (
                    ControlForm.verifyHashMapRedirect(
                            redirectAttributes,
                            err,
                            messageSource.getMessage("periodicity_tontine_validate_interest_member_offer_done", null, locale)
                    )
            ) {
                url = "details/$periodicity_id"
            }


        }
        return redirectTo(url)
    }

    fun injectAssociation(model: Model, association: Association) {
        model.addAttribute("association", association)
        val user = authenticationFacade.getAuthenticatedUser().get()
        var connectedUser = "actuator"

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
            UserType.ASSOCIATION_ADMIN -> {
                connectedUser = "association_admin"
                model.addAttribute("connectedUser", connectedUser)
            }
        }
    }

}