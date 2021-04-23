package com.b2i.tontine.application.controller.tontine

import org.springframework.stereotype.Controller
import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.association.port.AssociationDomain
import com.b2i.tontine.domain.tontine.port.TontineDomain
import org.springframework.context.MessageSource
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/20
 * @Time: 22:03
 */
@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_ASSOCIATION])
class TontineController(
    private val tontineDomain: TontineDomain,
    private val associationDomain: AssociationDomain,
    private val messageSource: MessageSource
) :
    BaseController("/backend/tontine/", ControllerEndpoint.BACKEND_ASSOCIATION) {

    @GetMapping("/{association_id}/tontines/create")
    fun goToCreateTontine(model: Model, @PathVariable association_id: String): String {
        injectAssociation(model, association_id)

        return forwardTo("add_tontine")
    }

    @GetMapping("/{association_id}/tontines")
    fun listOfAssociationTontines(model: Model, @PathVariable association_id: String): String {
        injectAssociation(model, association_id)

        return forwardTo("list_tontine")
    }

    //Inject association in view
    fun injectAssociation(model: Model, id: String) {
        val association: Association? = associationDomain.findAssociationById(id.toLong()).orElse(null)
        if (association != null) {
            model.addAttribute("association", association)
        }
    }

}