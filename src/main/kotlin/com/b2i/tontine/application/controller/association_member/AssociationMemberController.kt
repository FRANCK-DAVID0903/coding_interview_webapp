package com.b2i.tontine.application.controller.association_member

import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.application.facade.AuthenticationFacade
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/20
 * @Time: 22:02
 */
@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_ASSOCIATION_MEMBERS])
class AssociationMemberController(
        private val messageSource: MessageSource,
        private val authenticationFacade: AuthenticationFacade
) :
    BaseController("/backend/association_member/") {

    @GetMapping("/list_members")
    fun listOfAssociationMembers(model: Model): String {

        return forwardTo("list_association_member")
    }

}