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
import com.b2i.tontine.domain.tontine.entity.TontineType
import com.b2i.tontine.domain.tontine.port.TontineDomain
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
        private val tontineDomain: TontineDomain,
        private val messageSource: MessageSource

): BaseController(ControllerEndpoint.BACKEND_PERIODICITY) {

    @GetMapping("/details/{id}")
    fun goToListDetail(
            model: Model,
            @PathVariable id: String,
    ): String {

        val tontinePeriodicity = tontinePeriodicityDomain.findById(id.toLong()).orElse(null)

        if (tontinePeriodicity != null){
            model.addAttribute("tontinePeriodicityMembers", tontineContributionDomain.findAllByTontinePeriodicity(tontinePeriodicity))
            model.addAttribute("periodicity_id",id)
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
                                messageSource.getMessage("association_update_success", null, locale)
                        )
                ) {
                    url = "/$periodicity_id"
                }
            }


        }
        return redirectTo(url)
    }

    @PostMapping(value = ["/details/{periodicity_id}/price"])
    fun pricePeriodicity(
            redirectAttributes: RedirectAttributes,
            @PathVariable periodicity_id: String,
            @RequestParam id: String,
            @RequestParam payementMethod: String,
            @RequestParam contributionDate: String,
            locale: Locale
    ): String {
        var url = "periodicity/details/$periodicity_id"


        //get tontinePeriodicity by id
        val periodicity = tontinePeriodicityDomain.findById(periodicity_id.toLong()).orElse(null)

        if (periodicity != null){

            val contribution = TontineContribution()

            contribution.tontine = periodicity.tontine
            contribution.member = memberDomain.findById(id.toLong()).get()
            contribution.contributed = true
            contribution.tontinePeriodicity = periodicity
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
                            messageSource.getMessage("association_update_success", null, locale)
                    )
            ) {
                url = "periodicity/details/$periodicity_id"
            }

        }
        return redirectTo(url)
    }

}