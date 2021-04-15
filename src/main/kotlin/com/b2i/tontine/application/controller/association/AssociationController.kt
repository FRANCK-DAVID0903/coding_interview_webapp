package com.b2i.tontine.application.controller.association

import com.b2i.social.application.controlForm.ControlForm
import com.b2i.tontine.application.controlForm.Color
import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.association.port.AssociationDomain
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


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/15
 * @Time: 10:41
 */
@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_ASSOCIATION])
class AssociationController(
    private val associationDomain: AssociationDomain,
    private val messageSource: MessageSource
) : BaseController(ControllerEndpoint.BACKEND_ASSOCIATION) {

    @GetMapping("/create")
    fun goToCreateAssociation(model: Model): String {
        return forwardTo("add_association")
    }

    @PostMapping("/create")
    fun createAssociation(
        redirectAttributes: RedirectAttributes,
        @RequestParam("name") name: String,
        @RequestParam("description") description: String,
        @RequestParam("email") email: String,
        @RequestParam("phoneNumber") phoneNumber: String,
        locale: Locale
    ): String {

        var url = "/create"

        when {
            name.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("association_name_empty", null, locale),
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
                association.description = description
                association.email = email
                association.phoneNumber = phoneNumber

                val result: OperationResult<Association> = associationDomain.saveAssociation(association)

                if (
                    ControlForm.verifyHashMapRedirect(
                        redirectAttributes,
                        result.errors!!,
                        messageSource.getMessage("association_save_success", null, locale)
                    )
                ) {
                    url = "/associations"
                }

            }
        }

        return redirectTo(url)
    }

    @GetMapping("/list_associations")
    fun listOfAssociations(model: Model): String {
        val associations = associationDomain.findAllAssociations()
        model.addAttribute("associations", associations)

        return forwardTo("list_association")
    }

}