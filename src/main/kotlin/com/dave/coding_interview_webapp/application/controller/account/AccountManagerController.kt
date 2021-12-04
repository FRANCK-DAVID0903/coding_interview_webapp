package com.dave.coding_interview_webapp.application.controller.account

import com.dave.coding_interview_webapp.application.controller.BaseController
import com.dave.coding_interview_webapp.application.controller.ControllerEndpoint
import com.dave.coding_interview_webapp.application.facade.AuthenticationFacade
import com.dave.coding_interview_webapp.domain.account.port.RoleDomain
import com.dave.coding_interview_webapp.domain.account.port.UserDomain
import com.dave.coding_interview_webapp.infrastructure.local.storage.StorageService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*

@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_ACCOUNT])
class AccountManagerController(
    private val authenticationFacade: AuthenticationFacade,
    private val userDomain: UserDomain,
    private val roleDomain: RoleDomain,
    private val eventPublisher: ApplicationEventPublisher,
    private val storageService: StorageService,
    private val messageSource: MessageSource
) : BaseController(ControllerEndpoint.BACKEND_ACCOUNT) {

    @RequestMapping(value = ["/login"])
    fun login(
            model: Model
    ): String {
        model.addAttribute("currentLink", "login")
        return forwardTo("login")
    }

    @GetMapping(value = ["/register"])
    fun registerPage(
        model: Model,
        redirectAttributes: RedirectAttributes
    ): String {
        model.addAttribute("currentLink", "register")
        return forwardTo("register")
    }

    @PostMapping(value = ["/register"])
    fun registerSave(
            @RequestParam("firstname") firstname: String,
            @RequestParam("lastname") lastname: String,
            @RequestParam("tel") tel: String,
            @RequestParam("email") email: String,
            @RequestParam("dateNaissance") dateNaissance: String,
            @RequestParam("username") username: String,
            @RequestParam("password") password: String,
            @RequestParam("password2") password2: String,
            @RequestParam("img")img: MultipartFile,
            locale: Locale,
            redirectAttributes: RedirectAttributes
    ): String {

        var page = "account/register"

        return redirectTo(page)
    }
}