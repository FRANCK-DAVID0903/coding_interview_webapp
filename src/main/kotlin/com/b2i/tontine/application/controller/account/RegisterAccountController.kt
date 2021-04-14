package com.b2i.tontine.application.controller.account

import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(value = [ControllerEndpoint.REGISTER])
class RegisterAccountController: BaseController(ControllerEndpoint.REGISTER) {

    @GetMapping(value = ["/account", "/account"])
    fun register(): String {
        return ("/account/register")
    }
}