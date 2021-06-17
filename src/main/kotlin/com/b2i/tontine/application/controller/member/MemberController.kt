package com.b2i.tontine.application.controller.member

import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.member.port.MemberDomain
import com.b2i.tontine.domain.account.port.UserDomain
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.association.port.AssociationDomain
import com.b2i.tontine.domain.association_member.port.AssociationMemberDomain
import com.b2i.tontine.domain.contribution.port.ContributionDomain
import com.b2i.tontine.domain.tontine_contribution.port.TontineContributionDomain
import com.b2i.tontine.domain.tontine_periodicity.entity.TontinePeriodicity
import com.b2i.tontine.domain.tontine_periodicity.port.TontinePeriodicityDomain
import com.b2i.tontine.domain.tontine_request.port.TontineRequestDomain
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_MEMBERS])
class MemberController(
        private val associationDomain: AssociationDomain,
        private val userDomain: UserDomain,
        private val memberDomain: MemberDomain,
        private val messageSource: MessageSource,
        private val authenticationFacade: AuthenticationFacade,
        private val associationMemberDomain: AssociationMemberDomain,
        private val tontineRequestDomain: TontineRequestDomain,
        private val tontineContributionDomain: TontineContributionDomain,
        private val contributionDomain: ContributionDomain,
        private val tontinePeriodicityDomain: TontinePeriodicityDomain
): BaseController(ControllerEndpoint.BACKEND_MEMBERS) {


    @GetMapping("/my_associations")
    fun pageListAssociations(model: Model): String {

        val userConnected = authenticationFacade.getAuthenticatedUser().get()

        val associations = associationMemberDomain.findAllByUser(userConnected)
        model.addAttribute("associations", associations)
        return forwardTo("member_list_association")
    }


    @GetMapping("/my_tontines")
    fun pageListTontines(model: Model): String {

        val userConnected = authenticationFacade.getAuthenticatedUser().get()

        val memberConnected = memberDomain.findById(userConnected.id)

        val tontines = tontineRequestDomain.findAllByBeneficiary(memberConnected.get())
        val tontinesValidated = tontineRequestDomain.findAllByBeneficiaryAndState(memberConnected.get(),0)

        model.addAttribute("tontines", tontines)
        model.addAttribute("tontinesValidated", tontinesValidated)
        return forwardTo("member_list_tontine")
    }


    @GetMapping("/my_transactions")
    fun pageListTransactions(model: Model): String {

        val userConnected = authenticationFacade.getAuthenticatedUser().get()

        //Get member object
        var memberConnected = memberDomain.findById(userConnected.id).orElse(null)
        val associations = associationMemberDomain.findAllByUser(userConnected)

        var dataMemberContribute = mutableMapOf<Association,Triple<Double,Double,Double>>()

        //Montant global
        var allAmountContribute = contributionDomain.findAllByUserAndState(userConnected,0)
        var allAmountContributeTontine = tontineContributionDomain.findAllByMemberAndContributed(memberConnected,true)
        var allAmountInterestPayed = tontinePeriodicityDomain.findAllByBeneficiary(memberConnected)

        var montants : Triple<Double,Double,Double>

        associations.forEach{ asso ->

            val assoFinder : Association = associationDomain.findAssociationById(asso.association!!.id).orElse(null) ?: return@forEach
            val mtContribute = contributionDomain.findAllByUserAndAssociationAndState(userConnected,assoFinder,0).sumByDouble { it.amount }
            val mtTontine = tontineContributionDomain.findAllByMemberAndContributed(memberConnected,true).sumByDouble { it.contributionAmount }
            val mtInterest = tontinePeriodicityDomain.findAllByBeneficiaryAndTontine_Association(memberConnected, asso.association!!).sumByDouble { it.biddingAmount }
            montants = Triple(mtContribute,mtTontine,mtInterest)
            dataMemberContribute.put(assoFinder,montants)

        }

        model.addAttribute("associations", associations)
        model.addAttribute("allAmountContribute", allAmountContribute.sumByDouble { it.amount })
        model.addAttribute("allAmountContributeTontine", allAmountContributeTontine.sumByDouble { it.contributionAmount })
        model.addAttribute("associations", allAmountInterestPayed.sumByDouble { it.biddingAmount })

        model.addAttribute("allContributions",dataMemberContribute)


        return forwardTo("member_list_transaction")
    }

    @GetMapping("/my_transactions/detail_transactions/{association_id}")
    fun pageListDetailsTransactions(
            model: Model,
            @PathVariable association_id: String,
    ): String {

        val userConnected = authenticationFacade.getAuthenticatedUser().get()

        val association = associationDomain.findAssociationById(association_id.toLong()).orElse(null)

        var cotisations = contributionDomain.findAllByUserAndAssociationAndState(userConnected,association,0)
        var cotisationsNotValidated = contributionDomain.findAllByUserAndAssociationAndState(userConnected,association,1)


        model.addAttribute("cotisations", cotisations)
        model.addAttribute("cotisationsNotValidated", cotisationsNotValidated)

        return forwardTo("member_list_transaction_details")
    }
}
