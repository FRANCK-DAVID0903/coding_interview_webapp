package com.b2i.tontine.application.controller.tontine

import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/20
 * @Time: 22:03
 */
@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_TONTINE])
class TontineController(private val messageSource: MessageSource) : BaseController(ControllerEndpoint.BACKEND_TONTINE) {

    @GetMapping("/list_tontines")
    fun listOfAssociationTontines(model: Model): String {
        return forwardTo("list_tontine")
    }

}