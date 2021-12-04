package com.dave.coding_interview_webapp.domain.activity_sector.worker

import com.dave.coding_interview_webapp.utils.OperationResult
import com.dave.coding_interview_webapp.domain.activity_sector.port.ActivitySectorDomain
import com.dave.coding_interview_webapp.domain.activity_sector.entity.ActivitySector
import com.dave.coding_interview_webapp.infrastructure.db.repository.ActivitySectorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class ActivitySectorWorker: ActivitySectorDomain {

    @Autowired
    lateinit var activitySectorRepository: ActivitySectorRepository
    override fun saveActivitySector(activitySector: ActivitySector): OperationResult<ActivitySector> {

        val errors :MutableMap<String,String> = mutableMapOf()
        var data: ActivitySector? =null

        val activitySectorName = activitySectorRepository.findByLabel(activitySector?.label)

        if(activitySectorName.isPresent) {
            errors["name"]="Secteur d'activité déjà enregistré"
        }

        if (errors.isEmpty()) {
            data = activitySectorRepository.save(activitySector)
        }

        return OperationResult(data, errors)
    }

    override fun findAllActivitySector(): List<ActivitySector> = activitySectorRepository.findAll()

    override fun findByLabel(label: String): Optional<ActivitySector> = activitySectorRepository.findByLabel(label)

    override fun findById(id: Long): Optional<ActivitySector> = activitySectorRepository.findById(id)
    override fun countAllActivities(): Long {
        return activitySectorRepository.count()
    }


}
