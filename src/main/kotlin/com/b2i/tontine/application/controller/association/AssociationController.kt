package com.b2i.tontine.application.controller.association

import com.b2i.tontine.application.controlForm.ControlForm
import com.b2i.tontine.application.controlForm.Color
import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.port.UserDomain
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.association.port.AssociationDomain
import com.b2i.tontine.utils.OperationResult
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/15
 * @Time: 10:41
 */
@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_ASSOCIATION])
class AssociationController(
    private val associationDomain: AssociationDomain,
    private val messageSource: MessageSource,
    private val authenticationFacade: AuthenticationFacade,
    private val userDomain: UserDomain
) : BaseController(ControllerEndpoint.BACKEND_ASSOCIATION) {

    @GetMapping("/create")
    fun goToCreateAssociation(model: Model): String {
        val user = authenticationFacade.getAuthenticatedUser().get()
        model.addAttribute("userData",user)
        return forwardTo("add_association")
    }

    @PostMapping("/create")
    fun createAssociation(
        redirectAttributes: RedirectAttributes,
        @RequestParam("name") name: String,
        @RequestParam("acronym") acronym: String,
        @RequestParam("email") email: String,
        @RequestParam("phoneNumber") phoneNumber: String,
        @RequestParam("description") description: String,
        locale: Locale
    ): String {

        var url = "create"

        when {
            name.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("association_name_empty", null, locale),
                    Color.red
                )
            }
            acronym.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("association_acronym_empty", null, locale),
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
            phoneNumber.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("association_phoneNumber_empty", null, locale),
                    Color.red
                )
            }
            else -> {
                val association = Association()
                association.name = name
                association.acronym = acronym
                association.email = email
                association.phoneNumber = phoneNumber
                association.description = description

                val result: OperationResult<Association> = associationDomain.saveAssociation(association)

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
                        messageSource.getMessage("association_save_success", null, locale)
                    )
                ) {
                    url = "list_associations"
                }

            }
        }

        return redirectTo(url)
    }

    @PostMapping("/update")
    fun updateAssociation(redirectAttributes: RedirectAttributes,
                          @RequestParam("id") id: String,
                          @RequestParam("name") name: String,
                          @RequestParam("acronym") acronym: String,
                          @RequestParam("description") description: String,
                          locale: Locale) : String {

        var url = "detail_association"

        when {
            name.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("association_name_empty", null, locale),
                    Color.red
                )
            }
            acronym.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("association_acronym_empty", null, locale),
                    Color.red
                )
            }
            else -> {
                val association = Association()
                association.id = id.toLong()
                association.name = name
                association.acronym = acronym
                association.description = description

                val result: OperationResult<Association> = associationDomain.saveAssociation(association)

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
                        messageSource.getMessage("association_update_success", null, locale)
                    )
                ) {
                    url = "detail_association/$id"
                }

            }
        }

        return redirectTo(url)
    }

    @PostMapping("/update_mail")
    fun changeAssociationEmail(redirectAttributes: RedirectAttributes,
                               @RequestParam("id") id: String,
                               @RequestParam("email") email: String,
                               locale: Locale) : String {

        var url = "detail_association"

        when {
            email.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("association_email_empty", null, locale),
                    Color.red
                )
            }
            else -> {
                val result: OperationResult<Association> = associationDomain.updateAssociationEmail(id.toLong(), email)

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
                        messageSource.getMessage("association_update_success", null, locale)
                    )
                ) {
                    url = "detail_association/$id"
                }

            }
        }

        return  redirectTo(url)
    }

    @PostMapping("/update_phoneNumber")
    fun changeAssociationPhoneNumber(redirectAttributes: RedirectAttributes,
                               @RequestParam("id") id: String,
                               @RequestParam("phoneNumber") phoneNumber: String,
                               locale: Locale) : String {

        var url = "detail_association"

        when {
            phoneNumber.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("association_phoneNumber_empty", null, locale),
                    Color.red
                )
            }
            else -> {
                val result: OperationResult<Association> = associationDomain.updateAssociationPhoneNumber(id.toLong(), phoneNumber)

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
                        messageSource.getMessage("association_update_success", null, locale)
                    )
                ) {
                    url = "detail_association/$id"
                }

            }
        }

        return  redirectTo(url)
    }

    @GetMapping("/list_associations")
    fun listOfAssociations(model: Model): String {
        val associations = associationDomain.findAllAssociations()
        val user = authenticationFacade.getAuthenticatedUser().get()
        model.addAttribute("userData",user)
        model.addAttribute("associations", associations)

        return forwardTo("list_association")
    }

    @GetMapping(value = ["/detail_association/{id}"])
    fun associationDetail(model: Model, @PathVariable("id") id: String): String {
        var page: String = "detail_association"
        val association: Association? = associationDomain.findAssociationById(id.toLong()).orElse(null)

        if (association == null) {
            page = "list_association"
        } else {
            val user = authenticationFacade.getAuthenticatedUser().get()
            model.addAttribute("userData",user)
            model.addAttribute("association", association)
        }

        return forwardTo(page)
    }

//    fun injectDependance(model: Model){
//        val userC = authenticationFacade.getAuthenticatedUser().get()
//        val profil = UserProfiler.profile(userC)
//
//        if (profil.pfAdmin) {
//            model.addAttribute("allProject", projectWorker.findAllProjectByFocalPoint(userC.id))
//        } else{
//            model.addAttribute("allProject", projectWorker.findAllProject())
//        }
//
//        model.addAttribute("contacts", contactsDomain.findAllContacts().first())
//        model.addAttribute("regions", regionWorker.findAllRegion())
//    }

}