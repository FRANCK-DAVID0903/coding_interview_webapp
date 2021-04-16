package com.b2i.tontine.application.controller

import com.b2i.social.application.controlForm.ControlForm
import com.b2i.social.application.event.SendEmailEvent
import com.b2i.tontine.application.controlForm.Color
import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.entity.FolderSrc
import com.b2i.tontine.domain.account.entity.UserType
import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.account.member.port.MemberDomain
import com.b2i.tontine.domain.account.port.RoleDomain
import com.b2i.tontine.domain.account.port.UserDomain
import com.b2i.tontine.infrastructure.local.storage.StorageService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.text.SimpleDateFormat
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/12
 * @Time: 15:22
 */
@Controller
class MainController(
        private val authenticationFacade: AuthenticationFacade,
        private val userDomain: UserDomain,
        private val memberDomain: MemberDomain,
        private val roleDomain: RoleDomain,
        private val eventPublisher: ApplicationEventPublisher,
        private val storageService: StorageService
): BaseController(ControllerEndpoint.BACKEND_DASHBOARD) {

    @GetMapping(value = ["/backend", "/backend"])
    fun dashboardPage(
            model:Model,
            redirectAttributes: RedirectAttributes
    ): String {

        val user = authenticationFacade.getAuthenticatedUser().get()
        val userRoleId=authenticationFacade.getAuthenticatedUser().get().roleId()

        val userType = userDomain.findTypeBy(user.id)

        return when (userType)
        {

            UserType.ACTUATOR -> {
                forwardTo("dashboard/dashboard_actuator")
            }

            UserType.BACKOFFICE_ADMIN -> {

                redirectTo("dashboard/dashboard_admin")
            }

            UserType.BACKOFFICE_BASIC_USER -> {
                forwardTo("dashboard/dashboard_basic_user")
            }

            UserType.BACKOFFICE_SUPER_ADMIN -> {
                return "account/home"
            }

            else -> redirectTo("dashboard/dashboard_basic_user")
        }
    }

    @GetMapping(value = ["/account/register", "/account/register"])
    fun registerPage(
            model:Model,
            redirectAttributes: RedirectAttributes
    ): String {

        return "account/register"
    }

    @PostMapping(value = ["/account/register", "/account/register"])
    fun registerSave(
            model:Model,
            @RequestParam("firstname") firstname: String,
            @RequestParam("lastname") lastname: String,
            @RequestParam("tel") tel: String,
            @RequestParam("email") email: String,
            @RequestParam("dateNaissance") dateNaissance: String,
            @RequestParam("username") username: String,
            @RequestParam("password") password: String,
            @RequestParam("password2") password2: String,
            @RequestParam("img")img: MultipartFile,
            redirectAttributes: RedirectAttributes
    ): String {

        val member = Member()

        if (firstname.isEmpty()){
            ControlForm.redirectAttribute(redirectAttributes,"Veuilllez renseigner votre prénom", Color.red)
        }else if (lastname.isEmpty()){
            ControlForm.redirectAttribute(redirectAttributes,"Veuilllez renseigner votre nom", Color.red)
        }else if (tel.isEmpty()){
            ControlForm.redirectAttribute(redirectAttributes,"Veuilllez renseigner votre numéro de telephone", Color.red)
        }else if (email.isEmpty()){
            ControlForm.redirectAttribute(redirectAttributes,"Veuilllez renseigner votre email", Color.red)
        }else if (dateNaissance.isEmpty()){
            ControlForm.redirectAttribute(redirectAttributes,"Veuilllez renseigner votre date de naissance", Color.red)
        }else if (username.isEmpty()){
            ControlForm.redirectAttribute(redirectAttributes,"Veuilllez renseigner votre identifiant de mot de passe", Color.red)
        }else if (password !== password2 ){
            ControlForm.redirectAttribute(redirectAttributes,"Les mot de passe ne sont pas identiques", Color.red)
        }else if (userDomain.isTakenUserByEmail(email)) {
            ControlForm.redirectAttribute(redirectAttributes, "L email a déjà été utilisé", Color.red)
        } else if (userDomain.isTakenUserByUsername(username)) {
            ControlForm.redirectAttribute(redirectAttributes, "Le username a déjà été utilisé", Color.red)
        }
        else
        {
            roleDomain.findByName(UserType.ASSOCIATION_MEMBER).ifPresent{

                member.firstname = firstname
                member.lastname = lastname
                member.contact.mobile = tel
                member.contact.email = email
                member.birthday = SimpleDateFormat("YYYY-MM-dd").parse(dateNaissance)
                member.username = username
                member.password = password

                if (img.originalFilename != ""){
                    if(!storageService.storeMix(img,"", FolderSrc.SRC_MEMBER).first){
                        member.photo = img.originalFilename.toString().toLowerCase(Locale.FRENCH)
                    }else{
                        ControlForm.model(model,"Ajouter un fichier pour la photo", Color.red)
                    }
                }

                //On va save le gar maintenant
                memberDomain.saveMember(member)

                //Sans oublier de lui envvoyer ses parametres de connexion par mail
                eventPublisher.publishEvent(SendEmailEvent(member))


            }
        }



        return "account/login"
    }
}