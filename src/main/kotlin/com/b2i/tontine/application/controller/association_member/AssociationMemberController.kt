package com.b2i.tontine.application.controller.association_member

import com.b2i.tontine.application.controlForm.Color
import com.b2i.tontine.application.controlForm.ControlForm
import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.port.UserDomain
import com.b2i.tontine.domain.association.port.AssociationDomain
import com.b2i.tontine.domain.association_member.port.AssociationMemberDomain
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/20
 * @Time: 22:02
 */
@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_ASSOCIATION_MEMBERS])
class AssociationMemberController(
    private val associationMemberDomain: AssociationMemberDomain,
    private val associationDomain: AssociationDomain,
    private val userDomain: UserDomain,
    private val messageSource: MessageSource,
    private val authenticationFacade: AuthenticationFacade
) :
    BaseController("/backend/association_member/") {

    @GetMapping("/add")
    fun goToAddMemberInAssociation(model: Model): String {
        return forwardTo("add_association_member")
    }

    @PostMapping("/search")
    fun searchUserByEmailOrUsername(
        redirectAttributes: RedirectAttributes,
        @RequestParam("option") option: String,
        @RequestParam("value") value: String, locale: Locale
    ) {
        var url = "add"

        when {
            option.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("association_member_option_empty", null, locale),
                    Color.red
                )
            }
            value.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("association_member_value_empty", null, locale),
                    Color.red
                )
            }
            else -> {
                
            }
        }

    }

    @GetMapping("/list_members")
    fun listOfAssociationMembers(model: Model): String {

        return forwardTo("list_association_member")
    }

}