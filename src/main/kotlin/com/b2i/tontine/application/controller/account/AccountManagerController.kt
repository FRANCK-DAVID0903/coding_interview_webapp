package com.b2i.tontine.application.controller.account

import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_ACCOUNT])
class AccountManagerController : BaseController(ControllerEndpoint.BACKEND_ACCOUNT) {

    @RequestMapping(value = ["/login"])
    fun login(): String {
        return forwardTo("login")
    }
}