package com.b2i.social.domain.activity_sector.port

import com.dave.coding_interview_webapp.utils.OperationResult
import com.dave.coding_interview_webapp.domain.activity_sector.entity.ActivitySector
import java.util.*

interface IManageActivitySector {

    fun saveActivitySector(activitySector: ActivitySector): OperationResult<ActivitySector>

    fun findAllActivitySector(): List<ActivitySector>

    fun findByLabel(label:String): Optional<ActivitySector>

    fun findById(id:Long): Optional<ActivitySector>

    fun countAllActivities(): Long

    fun deleteById(activitySector: ActivitySector): Unit
}