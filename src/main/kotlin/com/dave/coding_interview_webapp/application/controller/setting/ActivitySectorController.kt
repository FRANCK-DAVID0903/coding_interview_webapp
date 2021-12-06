package com.dave.coding_interview_webapp.application.controller.setting

import com.dave.coding_interview_webapp.application.controlForm.Color
import com.dave.coding_interview_webapp.application.controlForm.ControlForm
import com.dave.coding_interview_webapp.application.controller.BaseController
import com.dave.coding_interview_webapp.application.controller.ControllerEndpoint
import com.dave.coding_interview_webapp.application.facade.AuthenticationFacade
import com.dave.coding_interview_webapp.domain.activity_sector.entity.ActivitySector
import com.dave.coding_interview_webapp.domain.activity_sector.port.ActivitySectorDomain
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping(value = ["/${ControllerEndpoint.BACKEND_SETTINGS}"])
class ActivitySectorController(private val authenticationFacade: AuthenticationFacade,
                               private val activitySectorDomain: ActivitySectorDomain

): BaseController(ControllerEndpoint.BACKEND_SETTINGS)  {

    @GetMapping(value=["","/activity-sector"])
    fun home(model: Model): String
    {
        model.addAttribute("allActivitySector",activitySectorDomain.findAllActivitySector())
        return forwardTo("activity_sector")
    }


    @PostMapping(value=["","/activity-sector"])
    fun addActivity(model: Model,
                    @RequestParam("id", required = false) id: String,
                    @RequestParam("name",required = true) name: String,
                    @RequestParam("description") description: String): String
    {
        var activitySector : ActivitySector? = null

        activitySector = if (id.isEmpty()) {
            ActivitySector()
        } else {
            activitySectorDomain.findById(id.toLong()).get()
        }

        activitySector.label=name
        activitySector.description=description

        activitySectorDomain.saveActivitySector(activitySector)
        ControlForm.model( model, "Votre opération a reussi", Color.green )

        model.addAttribute("allActivitySector",activitySectorDomain.findAllActivitySector())
        return forwardTo("activity_sector")
    }

    @PostMapping(value=["","/activity-sector/delete"])
    fun deleteActivity(model: Model,
                    @RequestParam("id", required = false) id: String
    ): String
    {
        var activitySector : ActivitySector? = null

        activitySector = activitySectorDomain.findById(id.toLong()).get()


        activitySectorDomain.deleteById(activitySector)
        ControlForm.model( model, "Votre opération a reussi", Color.green )

        model.addAttribute("allActivitySector",activitySectorDomain.findAllActivitySector())
        return forwardTo("activity_sector")
    }

}