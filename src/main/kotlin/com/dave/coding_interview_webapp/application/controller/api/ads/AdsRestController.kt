package com.dave.coding_interview_webapp.application.controller.api.ads

import com.dave.coding_interview_webapp.application.common.ResponseBody
import com.dave.coding_interview_webapp.application.common.ResponseSummary
import com.dave.coding_interview_webapp.application.controller.RestControllerEndpoint
import com.dave.coding_interview_webapp.domain.account.port.UserDomain
import com.dave.coding_interview_webapp.domain.activity_sector.port.ActivitySectorDomain
import com.dave.coding_interview_webapp.domain.ads.entity.Ads
import com.dave.coding_interview_webapp.domain.ads.port.AdsDomain
import com.dave.coding_interview_webapp.domain.client.port.ClientDomain
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

@RestController
@RequestMapping(RestControllerEndpoint.API_ADS)
class AdsRestController(
    private val userDomain: UserDomain,
    private val adsDomain: AdsDomain,
    private val clientDomain: ClientDomain,
    private val activitySectorDomain: ActivitySectorDomain
) {

    @PostMapping(value = ["/add-ads"])
    fun authenticateClient(
        @RequestParam("idClient") idClient: String,
        @RequestParam("idSector") idSector: String,
        @RequestParam("title") title: String,
        @RequestParam("date") date: String,
        @RequestParam("description") description: String

    ): ResponseEntity<ResponseBody<HashMap<String, Any>>> {

        var errors: MutableMap<String, String> = mutableMapOf()
        var data: HashMap<String,Any> = HashMap()



        if (idClient.isEmpty()) {
            errors["client"] = "Client introuvable"
        }

        if (idSector.isEmpty()) {
            errors["sector"] = "Secteur d'activité introuvable"
        }

        val client = clientDomain.findById(idClient.toLong()).orElse(null)
        val sector = activitySectorDomain.findById(idSector.toLong()).orElse(null)

        if (client == null){
            errors["client"] = "Client introuvable"
        }

        if (sector == null){
            errors["sector"] = "Secteur d'activité introuvable"
        }

        val ad = Ads()

        ad.title = title
        ad.client = client
        ad.activitySector = sector
        ad.description = description
        ad.date = SimpleDateFormat("yyyy-MM-dd").parse(date)


        if (errors.isEmpty()) {
            val result = adsDomain.saveAd(ad)
            if (result.errors!!.isEmpty()){
                data["result"] = "enregistrement terminé"
            } else{
                data["error"]="Une erreur est survenue"
            }

        }

        return ResponseEntity(ResponseBody(data, ResponseSummary(errors)), HttpStatus.OK)
    }
}