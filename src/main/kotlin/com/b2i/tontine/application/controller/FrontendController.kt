package com.b2i.tontine.application.controller

import com.b2i.tontine.application.controlForm.Color
import com.b2i.tontine.application.controlForm.ControlForm
import com.b2i.tontine.application.event.SendContactEmailEvent
import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.entity.UserNonRegistered
import com.b2i.tontine.domain.account.member.port.MemberDomain
import com.b2i.tontine.infrastructure.local.storage.StorageService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class FrontendController(
        private val authenticationFacade: AuthenticationFacade,
        private val eventPublisher: ApplicationEventPublisher,
        private val storageService: StorageService,
        private val memberDomain: MemberDomain,
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

    @GetMapping("/frontend-how-it-works")
    fun frontendHowItWorks(
            model: Model,
            redirectAttributes: RedirectAttributes
    ) : String {
        return "frontend/how-it-works"
    }

    @GetMapping("/frontend-faq")
    fun frontendFaq(
            model: Model,
            redirectAttributes: RedirectAttributes
    ) : String {
        return "frontend/faq"
    }

    @GetMapping("/frontend-contact-us")
    fun frontendContact(
            model: Model,
            redirectAttributes: RedirectAttributes
    ) : String {
        return "frontend/contact"
    }

    @PostMapping("/frontend-contact-us")
    fun submitFrontendContactForm(
            model: Model,
            redirectAttributes: RedirectAttributes,
            @RequestParam("fullname") fullname:String?="",
            @RequestParam("email") email:String?="",
            @RequestParam("message") message:String?=""
    ) : String {

        when {
            email.isNullOrEmpty()->{
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        "Le champ email est vide ou null",
                        Color.red
                )
            }
            fullname.isNullOrEmpty()->{
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        "Le champ fullname est vide ou null",
                        Color.red
                )
            }
            else->{
                val user = UserNonRegistered()
                user.email = email
                user.fullname = fullname
                user.msg = message
                try {
                    eventPublisher.publishEvent(SendContactEmailEvent(user))
                    ControlForm.redirectAttribute(
                            redirectAttributes,
                            "Votre message a bien été envoyé !",
                            Color.green
                    )
                } catch (e:Exception) {
                    ControlForm.redirectAttribute(
                            redirectAttributes,
                            e.message!!,
                            Color.red
                    )
                }
            }
        }

        return "redirect:/frontend-contact-us"
    }

}