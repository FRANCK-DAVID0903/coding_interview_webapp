package com.dave.coding_interview_webapp.infrastructure.db.repository

import com.dave.coding_interview_webapp.domain.activity_sector.entity.ActivitySector
import com.dave.coding_interview_webapp.domain.ads.entity.Ads
import com.dave.coding_interview_webapp.domain.client.entity.Client
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AdsRepository: JpaRepository<Ads,Long> {

    fun findAllByClient(client: Client): MutableList<Ads>

    fun findAllByActivitySector(activitySector: ActivitySector): MutableList<Ads>

    fun countDistinctByActivitySector(activitySector: ActivitySector): Long

    fun countAllByClient(client: Client): Long
}