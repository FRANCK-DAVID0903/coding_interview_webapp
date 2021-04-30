package com.b2i.tontine.application.controller.member

import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.member.port.MemberDomain
import com.b2i.tontine.domain.account.port.UserDomain
import com.b2i.tontine.domain.association.port.AssociationDomain
import com.b2i.tontine.domain.association_member.port.AssociationMemberDomain
import com.b2i.tontine.domain.tontine_request.port.TontineRequestDomain
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
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
        private val tontineRequestDomain: TontineRequestDomain
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

        val associations = associationMemberDomain.findAllByUser(userConnected)
        model.addAttribute("associations", associations)
        return forwardTo("member_list_transaction")
    }
}
