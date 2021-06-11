package com.b2i.tontine.application.controller.user

import com.b2i.tontine.application.controlForm.Color
import com.b2i.tontine.application.controlForm.ControlForm
import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.entity.Admin
import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.account.entity.UserType
import com.b2i.tontine.domain.account.port.RoleDomain
import com.b2i.tontine.domain.account.port.UserDomain
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.utils.OperationResult
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*

@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_USERS])
class userController(
        private val authenticationFacade: AuthenticationFacade,
        private val roleDomain: RoleDomain,
        private val userDomain: UserDomain,
        private val messageSource: MessageSource
):BaseController(ControllerEndpoint.BACKEND_USERS) {

    @GetMapping("/add-user")
    fun gotoAddUserPage(
            model: Model,
            locale: Locale
    ): String {
        return forwardTo("add_user")
    }

    @PostMapping("/create")
    fun createAssociation(
            redirectAttributes: RedirectAttributes,
            @RequestParam("name") name: String,
            @RequestParam("firstname") firstname: String,
            @RequestParam("email") email: String,
            @RequestParam("username") username: String,
            @RequestParam("password") password: String,
            locale: Locale
    ): String {

        var url = "add-user"

        when {
            name.isEmpty() -> {
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        messageSource.getMessage("user_name_empty", null, locale),
                        Color.red
                )
            }
            email.isEmpty() -> {
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        messageSource.getMessage("association_email_empty", null, locale),
                        Color.red
                )
            }
            else -> {
                val user = Admin()
                roleDomain.findByName(UserType.BACKOFFICE_ADMIN).ifPresent { role ->
                    user.lastname = name
                    user.firstname = firstname
                    user.contact.email = email
                    user.username = username
                    user.password = password
                    user.roles = Collections.singleton(role)
                }

                val result: OperationResult<User> = userDomain.saveUser(user)

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
                                messageSource.getMessage("user_save_success", null, locale)
                        )
                ) {
                    url = "list-user"
                }

            }
        }

        return redirectTo(url)
    }

    @GetMapping("/list-user")
    fun gotoListUserPage(
            model: Model,
            locale: Locale
    ): String {

        val admins = userDomain.findAllAdmin()
        model.addAttribute("admins",admins)
        return forwardTo("list_user")
    }


}