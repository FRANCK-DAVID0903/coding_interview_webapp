package com.dave.coding_interview_webapp.domain.report.worker

import com.dave.coding_interview_webapp.domain.ads.entity.Ads
import com.dave.coding_interview_webapp.domain.client.entity.Client
import com.dave.coding_interview_webapp.domain.report.entity.Report
import com.dave.coding_interview_webapp.domain.report.port.ReportDomain
import com.dave.coding_interview_webapp.infrastructure.db.repository.ReportRepository
import com.dave.coding_interview_webapp.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ReportWorker:ReportDomain {

    @Autowired
    lateinit var reportRepository: ReportRepository

    override fun countAllByAds(ads: Ads): Long {
        return reportRepository.countAllByAds(ads)
    }

    override fun findAllByAds(ads: Ads): MutableList<Report> {
        return reportRepository.findAllByAds(ads)
    }

    override fun findAllByClient(client: Client): MutableList<Report> {
        return reportRepository.findAllByClient(client)
    }

    override fun saveReport(report: Report): OperationResult<Report> {

        var data : Report? = null
        var errors : MutableMap<String,String> = mutableMapOf()

        if (errors.isEmpty()){
            data = reportRepository.save(report)
        }

        return OperationResult(data,errors)
    }
}