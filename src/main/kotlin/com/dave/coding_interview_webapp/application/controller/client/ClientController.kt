package com.dave.coding_interview_webapp.application.controller.client

import com.dave.coding_interview_webapp.application.controlForm.Color
import com.dave.coding_interview_webapp.application.controlForm.ControlForm
import com.dave.coding_interview_webapp.application.controller.BaseController
import com.dave.coding_interview_webapp.application.controller.ControllerEndpoint
import com.dave.coding_interview_webapp.domain.account.port.UserDomain
import com.dave.coding_interview_webapp.domain.client.port.ClientDomain
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping(value = ["/${ControllerEndpoint.BACKEND_CLIENT}"])
class ClientController(
    private val clientDomain: ClientDomain,
    private val userDomain: UserDomain
): BaseController(ControllerEndpoint.BACKEND_CLIENT) {

    @GetMapping(value=["","/list-clients"])
    fun home(model: Model): String
    {

        model.addAttribute("allClients",clientDomain.findAll())
        return forwardTo("list_clients")
    }

    @PostMapping(value=["","/change-status"])
    fun changeVisibility(model: Model,
                         @RequestParam("visible") visible : String,
                         @RequestParam("id") id : String

    ): String
    {

        if (visible.isEmpty()){
            ControlForm.model(model,"Une erreur est survenue", Color.red)
        }
        else{
            val client = clientDomain.findById(id.toLong()).orElse(null)

            client.status = visible.toInt()

            if (visible.toInt() == 2){
                client.locked = true
            }

            clientDomain.saveClient(client)

            ControlForm.model(model,"Enregistrement terminé", Color.red)
        }

        model.addAttribute("allClients",clientDomain.findAll())
        return forwardTo("list_clients")
    }
}