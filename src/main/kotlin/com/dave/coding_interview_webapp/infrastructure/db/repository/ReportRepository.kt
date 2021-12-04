package com.dave.coding_interview_webapp.infrastructure.db.repository

import com.dave.coding_interview_webapp.domain.ads.entity.Ads
import com.dave.coding_interview_webapp.domain.client.entity.Client
import com.dave.coding_interview_webapp.domain.report.entity.Report
import org.springframework.data.jpa.repository.JpaRepository

interface ReportRepository: JpaRepository<Report,Long> {

    fun countAllByAds(ads: Ads): Long

    fun findAllByAds(ads: Ads): MutableList<Report>

    fun findAllByClient(client: Client): MutableList<Report>
}