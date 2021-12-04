package com.dave.coding_interview_webapp.domain.report.port

import com.dave.coding_interview_webapp.domain.ads.entity.Ads
import com.dave.coding_interview_webapp.domain.client.entity.Client
import com.dave.coding_interview_webapp.domain.report.entity.Report
import com.dave.coding_interview_webapp.utils.OperationResult

interface IManageReport {

    fun countAllByAds(ads: Ads): Long

    fun findAllByAds(ads: Ads): MutableList<Report>

    fun findAllByClient(client: Client): MutableList<Report>

    fun saveReport(report: Report): OperationResult<Report>
}