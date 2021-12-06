package com.dave.coding_interview_webapp.application.controller.profileController

import com.dave.coding_interview_webapp.application.controlForm.Color
import com.dave.coding_interview_webapp.application.controlForm.ControlForm
import com.dave.coding_interview_webapp.application.controller.BaseController
import com.dave.coding_interview_webapp.application.controller.ControllerEndpoint
import com.dave.coding_interview_webapp.application.facade.AuthenticationFacade
import com.dave.coding_interview_webapp.domain.account.entity.FolderSrc
import com.dave.coding_interview_webapp.domain.account.port.UserDomain
import com.dave.coding_interview_webapp.domain.experience.entity.Experience
import com.dave.coding_interview_webapp.domain.experience.port.ExperienceDomain
import com.dave.coding_interview_webapp.domain.formation.entity.Formation
import com.dave.coding_interview_webapp.domain.formation.port.FormationDomain
import com.dave.coding_interview_webapp.domain.reference.entity.Reference
import com.dave.coding_interview_webapp.domain.reference.port.ReferenceDomain
import com.dave.coding_interview_webapp.domain.serviceProvider.entity.ServiceProvider
import com.dave.coding_interview_webapp.domain.serviceProvider.port.ServiceProviderDomain
import com.dave.coding_interview_webapp.infrastructure.local.storage.StorageService
import com.dave.coding_interview_webapp.utils.OperationResult
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*

@Controller
@RequestMapping(value = ["/${ControllerEndpoint.BACKEND_PROFILE}"])
class ProfileController(
    private val authenticationFacade: AuthenticationFacade,
    private val userDomain: UserDomain,
    private val serviceProviderDomain: ServiceProviderDomain,
    private val messageSource: MessageSource,
    private val formationDomain: FormationDomain,
    private val experienceDomain: ExperienceDomain,
    private val referenceDomain: ReferenceDomain,
    private val storageService: StorageService
): BaseController(ControllerEndpoint.BACKEND_PROFILE) {

    @GetMapping(value=["","/edit-profile"])
    fun editProfilePage(model: Model): String
    {

        val user = authenticationFacade.getAuthenticatedUser().get()

        model.addAttribute("provider",user)
        return forwardTo("edit_profile")
    }

    @PostMapping(value=["","/edit-profile"])
    fun updateInfo(
        @RequestParam("id") id: String,
        @RequestParam("firstname") firstname: String,
        @RequestParam("lastname") lastname: String,
        @RequestParam("tel") tel: String,
        @RequestParam("mobile") mobile: String,
        @RequestParam("rectoCNI") rectoCNI: MultipartFile,
        @RequestParam("versoCNI") versoCNI: MultipartFile,
        @RequestParam("bio")bio: String,
        locale: Locale,
        redirectAttributes: RedirectAttributes,
        model: Model
    ): String {


            val provider = serviceProviderDomain.findById(id.toLong()).orElse( null)

        if ( provider == null){
            ControlForm.redirectAttribute(redirectAttributes,"Prestataire introuvable", Color.red)
        }

            provider.firstname = firstname
            provider.lastname = lastname
            provider.contact.phone=tel
            provider.contact.mobile = mobile
            provider.bio = bio

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
        userDomain.saveUser(provider)

        model.addAttribute("provider",provider)
        ControlForm.model( model, "Modification terminée", Color.green )

        return forwardTo("edit_profile")
    }

    @PostMapping(value=["","/add-formation"])
    fun addFormation(model: Model,
                     redirectAttributes: RedirectAttributes,
                     locale: Locale,
                    @RequestParam("name",required = true) name: String,
                     @RequestParam("year",required = true) year: String,
                    @RequestParam("description") description: String): String
    {

        val userCo = authenticationFacade.getAuthenticatedUser().get()
        val provider = serviceProviderDomain.findById(userCo.id).orElse(null)

        val formation = Formation()

        formation.label = name
        formation.year = year
        formation.description = description

        if (provider != null){
            provider.formations.add(formation)
            formation.serviceProvider = provider
        }

        val result: OperationResult<ServiceProvider> = serviceProviderDomain.saveProvider(provider)

        val err: MutableMap<String, String> = mutableMapOf()
        if (result.errors!!.isNotEmpty()) {
            result.errors.forEach {
                    (key, value) -> err[key] = messageSource.getMessage(value, null, locale)
            }
        }
        model.addAttribute("provider",provider)
        ControlForm.model( model, "Votre opération a reussi", Color.green )

        //model.addAttribute("allActivitySector",activitySectorWorker.findAllActivitySector())
        return forwardTo("edit_profile")
    }

    @PostMapping(value=["","/delete-formation"])
    fun deleteFormation(model: Model,
                        redirectAttributes: RedirectAttributes,
                        locale: Locale,
                        @RequestParam("id",required = true) id: String,
    ):String
    {

        val userCo = authenticationFacade.getAuthenticatedUser().get()
        val provider = serviceProviderDomain.findById(userCo.id).orElse(null)

        val formation = formationDomain.findById(id.toLong()).orElse(null)

        if (formation != null){
            if (provider != null){
                provider.formations.remove(formation)
                formation.serviceProvider = provider
            }
        }


        val result: OperationResult<ServiceProvider> = serviceProviderDomain.saveProvider(provider)

        val err: MutableMap<String, String> = mutableMapOf()
        if (result.errors!!.isNotEmpty()) {
            result.errors.forEach {
                    (key, value) -> err[key] = messageSource.getMessage(value, null, locale)
            }
        }

        ControlForm.model( model, "Votre opération a reussi", Color.green )

        model.addAttribute("provider",provider)
        return forwardTo("edit_profile")
    }


    @PostMapping(value=["","/add-experience"])
    fun addExperience(model: Model,
                     redirectAttributes: RedirectAttributes,
                     locale: Locale,
                     @RequestParam("name",required = true) name: String,
                     @RequestParam("year",required = true) year: String,
                     @RequestParam("description") description: String): String
    {

        val userCo = authenticationFacade.getAuthenticatedUser().get()
        val provider = serviceProviderDomain.findById(userCo.id).orElse(null)

        val experience = Experience()

        experience.label = name
        experience.year = year
        experience.description = description

        if (provider != null){
            provider.experiences.add(experience)
            experience.serviceProvider = provider
        }

        val result: OperationResult<ServiceProvider> = serviceProviderDomain.saveProvider(provider)

        val err: MutableMap<String, String> = mutableMapOf()
        if (result.errors!!.isNotEmpty()) {
            result.errors.forEach {
                    (key, value) -> err[key] = messageSource.getMessage(value, null, locale)
            }
        }
        model.addAttribute("provider",provider)
        ControlForm.model( model, "Votre opération a reussi", Color.green )

        //model.addAttribute("allActivitySector",activitySectorWorker.findAllActivitySector())
        return forwardTo("edit_profile")
    }

    @PostMapping(value=["","/delete-experience"])
    fun deleteExperience(model: Model,
                        redirectAttributes: RedirectAttributes,
                        locale: Locale,
                        @RequestParam("id",required = true) id: String,
    ):String
    {

        val userCo = authenticationFacade.getAuthenticatedUser().get()
        val provider = serviceProviderDomain.findById(userCo.id).orElse(null)

        val experience = experienceDomain.findById(id.toLong()).orElse(null)

        if (experience != null){
            if (provider != null){
                provider.experiences.remove(experience)
                experience.serviceProvider = provider
            }
        }


        val result: OperationResult<ServiceProvider> = serviceProviderDomain.saveProvider(provider)

        val err: MutableMap<String, String> = mutableMapOf()
        if (result.errors!!.isNotEmpty()) {
            result.errors.forEach {
                    (key, value) -> err[key] = messageSource.getMessage(value, null, locale)
            }
        }

        ControlForm.model( model, "Votre opération a reussi", Color.green )

        model.addAttribute("provider",provider)
        return forwardTo("edit_profile")
    }

    @PostMapping(value=["","/add-reference"])
    fun addReference(model: Model,
                      redirectAttributes: RedirectAttributes,
                      locale: Locale,
                      @RequestParam("name",required = true) name: String,
                      @RequestParam("year",required = true) year: String,
                      @RequestParam("description") description: String): String
    {

        val userCo = authenticationFacade.getAuthenticatedUser().get()
        val provider = serviceProviderDomain.findById(userCo.id).orElse(null)

        val reference = Reference()

        reference.label = name
        reference.year = year
        reference.description = description

        if (provider != null){
            provider.references.add(reference)
            reference.serviceProvider = provider
        }

        val result: OperationResult<ServiceProvider> = serviceProviderDomain.saveProvider(provider)

        val err: MutableMap<String, String> = mutableMapOf()
        if (result.errors!!.isNotEmpty()) {
            result.errors.forEach {
                    (key, value) -> err[key] = messageSource.getMessage(value, null, locale)
            }
        }
        model.addAttribute("provider",provider)
        ControlForm.model( model, "Votre opération a reussi", Color.green )

        //model.addAttribute("allActivitySector",activitySectorWorker.findAllActivitySector())
        return forwardTo("edit_profile")
    }

    @PostMapping(value=["","/delete-reference"])
    fun deleteReference(model: Model,
                         redirectAttributes: RedirectAttributes,
                         locale: Locale,
                         @RequestParam("id",required = true) id: String,
    ):String
    {

        val userCo = authenticationFacade.getAuthenticatedUser().get()
        val provider = serviceProviderDomain.findById(userCo.id).orElse(null)

        val reference = referenceDomain.findById(id.toLong()).orElse(null)

        if (reference != null){
            if (provider != null){
                provider.references.remove(reference)
                reference.serviceProvider = provider
            }
        }


        val result: OperationResult<ServiceProvider> = serviceProviderDomain.saveProvider(provider)

        val err: MutableMap<String, String> = mutableMapOf()
        if (result.errors!!.isNotEmpty()) {
            result.errors.forEach {
                    (key, value) -> err[key] = messageSource.getMessage(value, null, locale)
            }
        }

        ControlForm.model( model, "Votre opération a reussi", Color.green )

        model.addAttribute("provider",provider)
        return forwardTo("edit_profile")
    }
}