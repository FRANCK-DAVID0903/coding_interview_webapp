package com.b2i.tontine.application.controller.account

import com.b2i.tontine.application.controlForm.ControlForm
import com.b2i.tontine.application.controlForm.Color
import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.application.event.SendEmailEvent
import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.entity.FolderSrc
import com.b2i.tontine.domain.account.entity.UserType
import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.account.member.port.MemberDomain
import com.b2i.tontine.domain.account.port.RoleDomain
import com.b2i.tontine.domain.account.port.UserDomain
import com.b2i.tontine.infrastructure.local.storage.StorageService
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
import java.text.SimpleDateFormat
import java.util.*

@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_ACCOUNT])
class AccountManagerController(
        private val authenticationFacade: AuthenticationFacade,
        private val userDomain: UserDomain,
        private val memberDomain: MemberDomain,
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
        val member = Member()

        when {
            firstname.isEmpty() -> {
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        messageSource.getMessage("user_firstname_empty", null, locale),
                        Color.red
                )
            }
            lastname.isEmpty() -> {
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        messageSource.getMessage("user_lastname_empty", null, locale),
                        Color.red
                )
            }
            tel.isEmpty() -> {
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        messageSource.getMessage("user_phone_empty", null, locale),
                        Color.red
                )
            }
            email.isEmpty() -> {
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        messageSource.getMessage("user_email_empty", null, locale),
                        Color.red
                )
            }
            dateNaissance.isEmpty() -> {
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        messageSource.getMessage("user_birthday_empty", null, locale),
                        Color.red
                )
            }
            username.isEmpty() -> {
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        messageSource.getMessage("user_username_empty", null, locale),
                        Color.red
                )
            }
            password != password2 -> {
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        messageSource.getMessage("user_passwords_different", null, locale),
                        Color.red
                )
            }
            userDomain.isTakenUserByEmail(email) -> {
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        messageSource.getMessage("user_email_always_used", null, locale),
                        Color.red
                )
            }
            userDomain.isTakenUserByUsername(username) -> {
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        messageSource.getMessage("user_username_always_used", null, locale),
                        Color.red
                )
            }
            else -> {
                roleDomain.findByName(UserType.ASSOCIATION_MEMBER).ifPresent{ role ->

                    member.firstname = firstname
                    member.lastname = lastname
                    member.contact.mobile = tel
                    member.contact.email = email
                    member.birthday = SimpleDateFormat("YYYY-MM-dd").parse(dateNaissance)
                    member.username = username
                    member.password = password
                    member.roles = Collections.singleton(role)

                    if (img.originalFilename != ""){
                        if(!storageService.storeMix(img,"", FolderSrc.SRC_MEMBER).first){
                            member.photo = img.originalFilename.toString().toLowerCase(Locale.FRENCH)
                        }else{
                            ControlForm.redirectAttribute(
                                    redirectAttributes,
                                    messageSource.getMessage("user_avatar_file_not_good", null, locale),
                                    Color.red
                            )
                        }
                    }

                    //On va save le gar maintenant
                    val result = memberDomain.saveMember(member)

                    if (
                            ControlForm.verifyHashMapRedirect(
                                    redirectAttributes,
                                    result.errors!!,
                                    messageSource.getMessage("member_save_success", null, locale)
                            )
                    ) {
                        page = "account/login"
                    }

                    //Sans oublier de lui envoyer ses parametres de connexion par mail
                    eventPublisher.publishEvent(SendEmailEvent(member))

                }
            }
        }

        return redirectTo(page)
    }
}