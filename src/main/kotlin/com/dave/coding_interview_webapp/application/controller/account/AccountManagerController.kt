package com.dave.coding_interview_webapp.application.controller.account

import com.dave.coding_interview_webapp.application.controlForm.Color
import com.dave.coding_interview_webapp.application.controlForm.ControlForm
import com.dave.coding_interview_webapp.application.controller.BaseController
import com.dave.coding_interview_webapp.application.controller.ControllerEndpoint
import com.dave.coding_interview_webapp.application.event.SendEmailConfirmAccount
import com.dave.coding_interview_webapp.application.event.SendEmailEvent
import com.dave.coding_interview_webapp.application.facade.AuthenticationFacade
import com.dave.coding_interview_webapp.domain.account.entity.FolderSrc
import com.dave.coding_interview_webapp.domain.account.entity.UserType
import com.dave.coding_interview_webapp.domain.account.port.RoleDomain
import com.dave.coding_interview_webapp.domain.account.port.UserDomain
import com.dave.coding_interview_webapp.domain.activity_sector.port.ActivitySectorDomain
import com.dave.coding_interview_webapp.domain.client.entity.Client
import com.dave.coding_interview_webapp.domain.serviceProvider.entity.ServiceProvider
import com.dave.coding_interview_webapp.domain.serviceProvider.port.ServiceProviderDomain
import com.dave.coding_interview_webapp.infrastructure.local.storage.StorageService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*

@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_ACCOUNT])
class AccountManagerController(
    private val authenticationFacade: AuthenticationFacade,
    private val userDomain: UserDomain,
    private val roleDomain: RoleDomain,
    private val activitySectorDomain: ActivitySectorDomain,
    private val serviceProviderDomain: ServiceProviderDomain,
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
        val allActivitySectors = activitySectorDomain.findAllActivitySector()

        model.addAttribute("allActivitySectors",allActivitySectors)
        return forwardTo("register")
    }

    @PostMapping(value = ["/register-prestataire"])
    fun registerSaveProvider(
            @RequestParam("firstname") firstname: String,
            @RequestParam("lastname") lastname: String,
            @RequestParam("tel") tel: String,
            @RequestParam("mobile") mobile: String,
            @RequestParam("email") email: String,
            @RequestParam("rectoCNI") rectoCNI: MultipartFile,
            @RequestParam("versoCNI") versoCNI: MultipartFile,
            @RequestParam("username") username: String,
            @RequestParam("password") password: String,
            @RequestParam("password2") password2: String,
            @RequestParam("sector")sector: String,
            @RequestParam("bio")bio: String,
            locale: Locale,
            redirectAttributes: RedirectAttributes,
            model: Model
    ): String {

        var page = "account/register"

        roleDomain.findByName(UserType.SERVICE_PROVIDER).ifPresent { role ->

            val provider = ServiceProvider()

            //var number = serviceProviderDomain.countAll()

            provider.firstname = firstname
            provider.lastname = lastname
            provider.contact.email=email
            provider.contact.phone=tel
            provider.contact.mobile = mobile
            provider.username = username
            provider.bio = bio
            provider.locked = true
            provider.roles = Collections.singleton(role)

            if(password != password2){
                ControlForm.model(model,"Les mots de passe ne sont pas identiques", Color.red)
            }else{
                provider.password = password
            }

            activitySectorDomain.findById(sector.toLong()).ifPresent { sector ->
                provider.activitySector = sector
            }

            if (rectoCNI.originalFilename != ""){
                if(!storageService.storeMix(rectoCNI,"", FolderSrc.SRC_SERVICE_PROVIDER).first){
                    provider.cniRecto = rectoCNI.originalFilename.toString().toLowerCase(Locale.FRENCH).replace(" ","")
                    //ControlForm.redirectAttribute(redirectAttributes,"La création du projet est terminée", Color.green)
                }else{
                    ControlForm.model(model,"Ajouter un fichier pour le recto de la CNI", Color.red)
                }
            }

            if (versoCNI.originalFilename != ""){
                if(!storageService.storeMix(versoCNI,"", FolderSrc.SRC_SERVICE_PROVIDER).first){
                    provider.cniVerso = versoCNI.originalFilename.toString().toLowerCase(Locale.FRENCH).replace(" ","")
                    //ControlForm.redirectAttribute(redirectAttributes,"La création du projet est terminée", Color.green)
                }else{
                    ControlForm.model(model,"Ajouter un fichier pour le recto de la CNI", Color.red)
                }
            }

            //Email
            if (!email.isEmpty()){
                eventPublisher.publishEvent(SendEmailConfirmAccount(provider))
            }


            userDomain.saveUser(provider)

            ControlForm.model( model, "Compte crée avec succès, veuillez consulter vos emails pour activation", Color.green )
            page = "account/login"
        }

        return redirectTo(page)
    }

    @RequestMapping(value = ["/email/confirm-account/{username}"])
    fun confirmAccountCreate(
        model: Model,
        @PathVariable("username") username: String
    ): String {

        val user = userDomain.findByUsername(username).orElse(null)

        user.locked = false

        userDomain.saveUser(user)

        if (user !=null){
            if (!user.contact.email.isEmpty()){
                eventPublisher.publishEvent(SendEmailEvent(user))
            }
        }

        //model.addAttribute("currentLink", "login")
        return forwardTo("confirm_account")
    }


    @PostMapping(value = ["/register-client"])
    fun registerSaveClient(
        @RequestParam("firstname") firstname: String,
        @RequestParam("lastname") lastname: String,
        @RequestParam("email") email: String,
        @RequestParam("username") username: String,
        @RequestParam("password") password: String,
        @RequestParam("password2") password2: String,
        locale: Locale,
        redirectAttributes: RedirectAttributes,
        model: Model
    ): String {

        var page = "account/register"

        roleDomain.findByName(UserType.CLIENT).ifPresent { role ->

            val client = Client()

            client.firstname = firstname
            client.lastname = lastname
            client.contact.email=email
            client.username = username
            client.roles = Collections.singleton(role)

            if(password != password2){
                ControlForm.model(model,"Les mots de passe ne sont pas identiques", Color.red)
            }else{
                client.password = password
            }

            //Email
            if (!email.isEmpty()){
                eventPublisher.publishEvent(SendEmailEvent(client))
            }


            userDomain.saveUser(client)

            ControlForm.model( model, "Compte crée avec succès, veuillez consulter vos emails pour activation", Color.green )
            page = "account/login"
        }

        return redirectTo(page)
    }

}