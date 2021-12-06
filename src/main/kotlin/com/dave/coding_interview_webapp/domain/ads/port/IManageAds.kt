package com.dave.coding_interview_webapp.domain.ads.port

import com.dave.coding_interview_webapp.domain.activity_sector.entity.ActivitySector
import com.dave.coding_interview_webapp.domain.ads.entity.Ads
import com.dave.coding_interview_webapp.domain.client.entity.Client
import com.dave.coding_interview_webapp.utils.OperationResult
import java.util.*

interface IManageAds {

    fun saveAd(ads:Ads): OperationResult<Ads>

    fun findAll():MutableList<Ads>

    fun findAllByClient(client: Client): MutableList<Ads>

    fun findAllByActivitySector(activitySector: ActivitySector): MutableList<Ads>

    fun countDistinctByActivitySector(activitySector: ActivitySector): Long

    fun countAllByClient(client: Client): Long

    fun findById(id:Long): Optional<Ads>
}