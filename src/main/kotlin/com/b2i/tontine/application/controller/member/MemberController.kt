package com.b2i.tontine.application.controller.member

import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.member.port.MemberDomain
import com.b2i.tontine.domain.account.port.UserDomain
import com.b2i.tontine.domain.association.port.AssociationDomain
import com.b2i.tontine.domain.association_member.port.AssociationMemberDomain
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
        private val associationMemberDomain: AssociationMemberDomain
): BaseController(ControllerEndpoint.BACKEND_MEMBERS) {


    @GetMapping("/my_associations")
    fun goToCreateAssociation(model: Model): String {

        val userConnected = authenticationFacade.getAuthenticatedUser().get()

        val associations = associationMemberDomain.findAllByMember(userConnected)
        model.addAttribute("associations", associations)
        return forwardTo("member_list_association")
    }
}
