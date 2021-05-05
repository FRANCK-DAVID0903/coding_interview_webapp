package com.b2i.tontine.application.controller.tontinecontribution

import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.port.UserDomain
import com.b2i.tontine.domain.tontine_periodicity.port.TontinePeriodicityDomain
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_PERIODICITY])
class TontineContributionController(
        private val authenticationFacade: AuthenticationFacade,
        private val userDomain: UserDomain,
        private val tontinePeriodicityDomain: TontinePeriodicityDomain
): BaseController(ControllerEndpoint.BACKEND_PERIODICITY) {

    @GetMapping("/list")
    fun goToList(model: Model): String {
        return forwardTo("list_contribution")
    }
}