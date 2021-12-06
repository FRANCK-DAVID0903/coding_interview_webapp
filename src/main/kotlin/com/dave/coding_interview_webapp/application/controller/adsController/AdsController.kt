package com.dave.coding_interview_webapp.application.controller.adsController

import com.dave.coding_interview_webapp.application.controlForm.Color
import com.dave.coding_interview_webapp.application.controlForm.ControlForm
import com.dave.coding_interview_webapp.application.controller.BaseController
import com.dave.coding_interview_webapp.application.controller.ControllerEndpoint
import com.dave.coding_interview_webapp.application.facade.AuthenticationFacade
import com.dave.coding_interview_webapp.domain.activity_sector.port.ActivitySectorDomain
import com.dave.coding_interview_webapp.domain.ads.entity.Ads
import com.dave.coding_interview_webapp.domain.ads.port.AdsDomain
import com.dave.coding_interview_webapp.domain.client.port.ClientDomain
import com.dave.coding_interview_webapp.domain.serviceProvider.port.ServiceProviderDomain
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.text.SimpleDateFormat

@Controller
@RequestMapping(value = ["/${ControllerEndpoint.BACKEND_ADS}"])
class AdsController(
    private val authenticationFacade: AuthenticationFacade,
    private val adsDomain: AdsDomain,
    private val activitySectorDomain: ActivitySectorDomain,
    private val clientDomain: ClientDomain,
    private val serviceProviderDomain: ServiceProviderDomain
): BaseController(ControllerEndpoint.BACKEND_ADS) {

    @GetMapping(value=["","/list-ads"])
    fun home(model: Model): String
    {

        model.addAttribute("allAds",adsDomain.findAll())
        return forwardTo("list_ads")
    }

    @GetMapping(value=["","/add-ads"])
    fun addAdsPage(model: Model): String
    {

        model.addAttribute("sectors",activitySectorDomain.findAllActivitySector())
        return forwardTo("add_ads")
    }

    @GetMapping(value=["","/my-ads"])
    fun myAdsPage(model: Model): String
    {
        val userConnect = authenticationFacade.getAuthenticatedUser().get()

        val provider = serviceProviderDomain.findById(userConnect.id).orElse(null)

        if(provider != null){
            model.addAttribute("sectors",activitySectorDomain.findAllActivitySector())
        }

        val sector = provider.activitySector

        model.addAttribute("allAds", sector?.let { adsDomain.findAllByActivitySectorAndStatus(it,1) })
        return forwardTo("my_ads")
    }

    @PostMapping(value=["","/add-ads"])
    fun addAds(model: Model,
               @RequestParam("title") title : String,
               @RequestParam("date") date : String,
               @RequestParam("sector") sector : String,
               @RequestParam("description") description : String,

    ): String
    {
        val userConnect = authenticationFacade.getAuthenticatedUser().get()

        val client = clientDomain.findById(userConnect.id).orElse(null)

        val ad = Ads()
        ad.title = title
        ad.client = client
        ad.status = 0
        if (sector.isEmpty()){
            ControlForm.model(model,"Secteur vide",Color.red)
        }else{
            val activitySector = activitySectorDomain.findById(sector.toLong()).orElse(null)
            ad.activitySector = activitySector
        }

        ad.description = description
        ad.date = SimpleDateFormat("yyyy-MM-dd").parse(date)

        val result = adsDomain.saveAd(ad)
        if (result.errors!!.isEmpty()){
            ControlForm.model(model,"Enregistrement terminé",Color.red)
        } else{
            ControlForm.model(model,"Une erreur est survenue",Color.red)
        }

        model.addAttribute("allAds",adsDomain.findAll())
        return forwardTo("list_ads")
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
            val ad = adsDomain.findById(id.toLong()).orElse(null)

            ad.status = visible.toInt()

            adsDomain.saveAd(ad)

            ControlForm.model(model,"Enregistrement terminé", Color.red)
        }

        model.addAttribute("allAds",adsDomain.findAll())
        return forwardTo("list_ads")
    }
}