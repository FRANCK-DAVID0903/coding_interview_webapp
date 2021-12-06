package com.dave.coding_interview_webapp.domain.ads.worker

import com.dave.coding_interview_webapp.domain.activity_sector.entity.ActivitySector
import com.dave.coding_interview_webapp.domain.ads.entity.Ads
import com.dave.coding_interview_webapp.domain.ads.port.AdsDomain
import com.dave.coding_interview_webapp.domain.client.entity.Client
import com.dave.coding_interview_webapp.infrastructure.db.repository.AdsRepository
import com.dave.coding_interview_webapp.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class AdsWorker:AdsDomain {

    @Autowired
    lateinit var  adsRepository: AdsRepository
    override fun saveAd(ads: Ads): OperationResult<Ads> {
        var data : Ads? = null
        var errors: MutableMap<String,String> = mutableMapOf()

        if (errors.isEmpty()){
            data = adsRepository.save(ads)
        }

        return OperationResult(data, errors)
    }

    override fun findAll(): MutableList<Ads> {
        return adsRepository.findAll()
    }

    override fun findAllByClient(client: Client): MutableList<Ads> {
        return adsRepository.findAllByClient(client)
    }

    override fun findAllByActivitySector(activitySector: ActivitySector): MutableList<Ads> {
        return adsRepository.findAllByActivitySector(activitySector)
    }

    override fun countDistinctByActivitySector(activitySector: ActivitySector): Long {
        return adsRepository.countDistinctByActivitySector(activitySector)
    }

    override fun countAllByClient(client: Client): Long {
        return adsRepository.countAllByClient(client)
    }

    override fun findById(id: Long): Optional<Ads> {
        return adsRepository.findById(id)
    }

}