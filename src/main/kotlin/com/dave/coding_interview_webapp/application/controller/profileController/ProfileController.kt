package com.dave.coding_interview_webapp.application.controller.profileController

import com.dave.coding_interview_webapp.application.controlForm.Color
import com.dave.coding_interview_webapp.application.controlForm.ControlForm
import com.dave.coding_interview_webapp.application.controller.BaseController
import com.dave.coding_interview_webapp.application.controller.ControllerEndpoint
import com.dave.coding_interview_webapp.application.facade.AuthenticationFacade
import com.dave.coding_interview_webapp.domain.account.port.UserDomain
import com.dave.coding_interview_webapp.domain.activity_sector.entity.ActivitySector
import com.dave.coding_interview_webapp.domain.serviceProvider.port.ServiceProviderDomain
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping(value = ["/${ControllerEndpoint.BACKEND_PROFILE}"])
class ProfileController(
    private val authenticationFacade: AuthenticationFacade,
    private val userDomain: UserDomain,
    private val serviceProviderDomain: ServiceProviderDomain
): BaseController(ControllerEndpoint.BACKEND_PROFILE) {

    @GetMapping(value=["","/edit"])
    fun editProfilePage(model: Model): String
    {
        //model.addAttribute("personalInfo",activitySectorWorker.findAllActivitySector())
        return forwardTo("edit-profile")
    }

    @PostMapping(value=["","/change_personal_info"])
    fun addActivity(model: Model,
                    @RequestParam("id", required = false) id: String,
                    @RequestParam("name",required = true) name: String,
                    @RequestParam("description") description: String): String
    {

        //activitySectorWorker.saveActivitySector(activitySector)
        //ControlForm.model( model, "Votre opération a reussi", Color.green )

        //model.addAttribute("allActivitySector",activitySectorWorker.findAllActivitySector())
        return forwardTo("edit-profile")
    }
}