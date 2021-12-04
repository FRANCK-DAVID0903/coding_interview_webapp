package com.dave.coding_interview_webapp.infrastructure.db.repository

import com.dave.coding_interview_webapp.domain.activity_sector.entity.ActivitySector
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ActivitySectorRepository:JpaRepository<ActivitySector,Long> {

    fun findByLabel(label:String):Optional<ActivitySector>
}