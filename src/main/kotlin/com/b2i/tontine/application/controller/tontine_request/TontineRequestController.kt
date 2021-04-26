package com.b2i.tontine.application.controller.tontine_request

import com.b2i.tontine.application.controlForm.Color
import com.b2i.tontine.application.controlForm.ControlForm
import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.association.port.AssociationDomain
import com.b2i.tontine.domain.association_member.entity.AssociationMember
import com.b2i.tontine.domain.association_member.port.AssociationMemberDomain
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine.port.TontineDomain
import com.b2i.tontine.domain.tontine_request.entity.TontineRequest
import com.b2i.tontine.domain.tontine_request.port.TontineRequestDomain
import com.b2i.tontine.utils.OperationResult
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/25
 * @Time: 17:44
 */
@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_ASSOCIATION])
class TontineRequestController(
    private val tontineRequestDomain: TontineRequestDomain,
    private val associationDomain: AssociationDomain,
    private val associationMemberDomain: AssociationMemberDomain,
    private val tontineDomain: TontineDomain,
    private val messageSource: MessageSource
) : BaseController("/backend/tontine/", ControllerEndpoint.BACKEND_ASSOCIATION) {

    @GetMapping("/{association_id}/tontines/{tontine_id}/addMember")
    fun goToCreateTontineRequest(model: Model,
                                 @PathVariable association_id: String,
                                 @PathVariable tontine_id: String,): String {
        injectValues(model, association_id, tontine_id)

        return forwardTo("add_tontine")
    }

    @GetMapping(value = ["/{association_id}/tontines/{tontine_id}/addMember"])
    fun createTontineRequest(
        redirectAttributes: RedirectAttributes,
        @PathVariable association_id: String,
        @PathVariable tontine_id: String,
        @RequestParam id: String,
        locale: Locale
    ): String {
        var url = "$association_id/tontines"

        when {
            association_id.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("association_member_id_association_empty", null, locale),
                    Color.red
                )
            }
            tontine_id.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontine_id_empty", null, locale),
                    Color.red
                )
            }
            else -> {
                val result: OperationResult<TontineRequest> =
                    tontineRequestDomain.createTontineRequest(tontine_id.toLong(), id.toLong())

                val err: MutableMap<String, String> = mutableMapOf()
                if (result.errors!!.isNotEmpty()) {
                    result.errors.forEach { (key, value) ->
                        err[key] = messageSource.getMessage(value, null, locale)
                    }
                }

                if (
                    ControlForm.verifyHashMapRedirect(
                        redirectAttributes,
                        err,
                        messageSource.getMessage("tontine_request_success", null, locale)
                    )
                ) {
                    url = "$association_id/tontines/$tontine_id"
                }

            }
        }

        return redirectTo(url)
    }


    //Inject association in view
    fun injectValues(model: Model, association_id: String, tontine_id: String) {
        val association: Association? = associationDomain.findAssociationById(association_id.toLong()).orElse(null)
        val tontine: Tontine? = tontineDomain.findTontineById(tontine_id.toLong()).orElse(null)

        if (association != null && tontine != null) {
            val associationMembers: List<AssociationMember> = associationMemberDomain.findAllMembersInAssociation(association)

            model.addAttribute("associationMembers", associationMembers)
            model.addAttribute("association", association)
            model.addAttribute("tontine", tontine)
        }
    }
}