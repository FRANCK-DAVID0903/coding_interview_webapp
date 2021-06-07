package com.b2i.tontine.application.controller

import com.b2i.tontine.application.controlForm.Color
import com.b2i.tontine.application.controlForm.ControlForm
import com.b2i.tontine.application.event.SendContactEmailEvent
import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.account.entity.UserNonRegistered
import com.b2i.tontine.domain.account.member.entity.Member
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

    @GetMapping("/frontend-sign-up")
    fun frontendRegister(
            model: Model,
            redirectAttributes: RedirectAttributes
    ) : String {
        return "frontend/register"
    }

    @PostMapping("/frontend-sign-up")
    fun submitFrontendSignupForm(
            model: Model,
            redirectAttributes: RedirectAttributes,
            @RequestParam("firstname") firstname:String?="",
            @RequestParam("lastname") lastname:String?="",
            @RequestParam("username") username:String?="",
            @RequestParam("mdp") mdp:String?="",
            @RequestParam("phone") phone:String?="",
            @RequestParam("email") email:String?="",
    ): String {
        when {
            firstname.isNullOrEmpty()->{
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        "la champ firstname est null ou vide",
                        Color.red
                )
            }
            lastname.isNullOrEmpty()->{
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        "la champ lastname est null ou vide",
                        Color.red
                )
            }
            username.isNullOrEmpty()->{
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        "la champ username est null ou vide",
                        Color.red
                )
            }
            mdp.isNullOrEmpty()->{
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        "la champ mot de passe est null ou vide",
                        Color.red
                )
            }
            phone.isNullOrEmpty()->{
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        "la champ contact est null ou vide",
                        Color.red
                )
            }
            email.isNullOrEmpty()->{
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        "la champ adresse mail est null ou vide",
                        Color.red
                )
            }
            else->{
                val user = Member()
                user.firstname = firstname
                user.lastname = lastname
                user.username = username
                user.password = mdp
                user.contact.phone = phone
                user.contact.email = email

                try {
                    memberDomain.saveMember(user)
                    ControlForm.redirectAttribute(
                            redirectAttributes,
                            "Opération effectuée avec succès, Votre compte a bien été enregistré !",
                            Color.green
                    )
                    return "redirect:/frontend-home"
                } catch (e:Exception) {
                    ControlForm.redirectAttribute(
                            redirectAttributes,
                            e.message!!,
                            Color.red
                    )
                }
            }
        }

        return "redirect:/frontend-sign-up"
    }

}