package com.dave.coding_interview_webapp.application.controller.serviceProviderController

import com.dave.coding_interview_webapp.application.controlForm.Color
import com.dave.coding_interview_webapp.application.controlForm.ControlForm
import com.dave.coding_interview_webapp.application.controller.BaseController
import com.dave.coding_interview_webapp.application.controller.ControllerEndpoint
import com.dave.coding_interview_webapp.application.facade.AuthenticationFacade
import com.dave.coding_interview_webapp.domain.account.port.UserDomain
import com.dave.coding_interview_webapp.domain.serviceProvider.port.ServiceProviderDomain
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping(value = ["/${ControllerEndpoint.BACKEND_SERVICE_PROVIDER}"])
class ServiceProviderController(
    private val authencationFacade: AuthenticationFacade,
    private val userDomain: UserDomain,
    private val serviceProviderDomain: ServiceProviderDomain

): BaseController(ControllerEndpoint.BACKEND_SERVICE_PROVIDER) {

    @GetMapping(value=["","/list"])
    fun home(model: Model): String
    {

        model.addAttribute("allServiceProviders",serviceProviderDomain.findAll())
        return forwardTo("list_service_providers")
    }

    @PostMapping(value=["","/change-visibility"])
    fun changeVisibility(model: Model,
                         @RequestParam("visible") visible : String,
                         @RequestParam("id") id : String

    ): String
    {

        if (visible.isEmpty()){
            ControlForm.model(model,"Une erreur est survenue", Color.red)
        }
        else{
            val provider = serviceProviderDomain.findById(id.toLong()).orElse(null)

            provider.status = visible.toInt()

            serviceProviderDomain.saveProvider(provider)
        }

        model.addAttribute("allServiceProviders",serviceProviderDomain.findAll())
        return forwardTo("list_service_providers")
    }
}