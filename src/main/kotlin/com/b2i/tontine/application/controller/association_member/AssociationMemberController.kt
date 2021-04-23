package com.b2i.tontine.application.controller.association_member

import com.b2i.tontine.application.controlForm.Color
import com.b2i.tontine.application.controlForm.ControlForm
import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.account.port.UserDomain
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.association.port.AssociationDomain
import com.b2i.tontine.domain.association_member.entity.AssociationMember
import com.b2i.tontine.domain.association_member.port.AssociationMemberDomain
import com.b2i.tontine.utils.OperationResult
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/20
 * @Time: 22:02
 */
@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_ASSOCIATION])
class AssociationMemberController(
    private val associationMemberDomain: AssociationMemberDomain,
    private val associationDomain: AssociationDomain,
    private val userDomain: UserDomain,
    private val messageSource: MessageSource,
    private val authenticationFacade: AuthenticationFacade
) :
    BaseController("/backend/association_member/", ControllerEndpoint.BACKEND_ASSOCIATION) {

    @GetMapping("/{association_id}/members/add")
    fun goToAddMemberInAssociation(model: Model, @PathVariable association_id: String): String {
        injectAssociation(model, association_id)

        return forwardTo("add_association_member")
    }

    @PostMapping("/{association_id}/members/add_member")
    fun addMemberInAssociation(
        redirectAttributes: RedirectAttributes,
        @RequestParam("id") user_id: String,
        @PathVariable association_id: String, locale: Locale
    ): String {
        var url = "$association_id/members/add"

        when {
            user_id.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("association_member_id_user_empty", null, locale),
                    Color.red
                )
            }
            association_id.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("association_member_id_association_empty", null, locale),
                    Color.red
                )
            }
            else -> {
                val result: OperationResult<AssociationMember> =
                    associationMemberDomain.addMemberToAssociation(association_id.toLong(), user_id.toLong())

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
                        messageSource.getMessage("association_member_add_success", null, locale)
                    )
                ) {
                    url = "$association_id/members"
                }
            }
        }

        return redirectTo(url)
    }

    @PostMapping("/{association_id}/members/search")
    fun searchUserByEmailOrUsername(
        redirectAttributes: RedirectAttributes,
        @RequestParam("option") option: String,
        @RequestParam("value") value: String, locale: Locale, @PathVariable association_id: String
    ): String {

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
                var user: Optional<User>? = null

                if (option == "email") {
                    user = userDomain.findByEmail(value)
                } else if (option == "username") {
                    user = userDomain.findByUsername(value)
                }

                if (user!!.isEmpty) {
                    redirectAttributes.addFlashAttribute("userSearchNull", true)
                } else {
                    redirectAttributes.addFlashAttribute("userSearchNull", false)
                    redirectAttributes.addFlashAttribute("userSearch", user.get())
                }

            }
        }

        return redirectTo("$association_id/members/add")
    }

    @GetMapping("/{association_id}/members")
    fun listOfAssociationMembers(
        model: Model,
        @PathVariable association_id: String,
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
                    val members = associationMemberDomain.findAllMembersInAssociation(association.get())
                    model.addAttribute("association_members", members)
                }
            }
        }

        injectAssociation(model, association_id)

        return forwardTo("list_association_member")
    }

    //Inject association in view
    fun injectAssociation(model: Model, id: String) {
        val association: Association? = associationDomain.findAssociationById(id.toLong()).orElse(null)
        if (association != null) {
            model.addAttribute("association", association)
        }
    }

}