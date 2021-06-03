package com.b2i.tontine.application.controller

import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.infrastructure.local.storage.StorageService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class FrontendController(
        private val authenticationFacade: AuthenticationFacade,
        private val eventPublisher: ApplicationEventPublisher,
        private val storageService: StorageService
) {

    @GetMapping("/")
    fun Home(
            model: Model,
            redirectAttributes: RedirectAttributes
    ) : String {
        return "frontend/home"
    }

    @GetMapping("/frontend-home")
    fun frontendHome(
            model: Model,
            redirectAttributes: RedirectAttributes
    ) : String {
        return "frontend/home"
    }

    @GetMapping("/frontend-contact-us")
    fun frontendContact(
            model: Model,
            redirectAttributes: RedirectAttributes
    ) : String {
        return "frontend/contact"
    }

    @GetMapping("/frontend-sign-up")
    fun frontendRegister(
            model: Model,
            redirectAttributes: RedirectAttributes
    ) : String {
        return "frontend/register"
    }

}