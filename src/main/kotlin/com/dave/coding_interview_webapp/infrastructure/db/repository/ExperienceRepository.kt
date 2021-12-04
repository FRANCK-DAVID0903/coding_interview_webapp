package com.dave.coding_interview_webapp.infrastructure.db.repository

import com.dave.coding_interview_webapp.domain.experience.entity.Experience
import org.springframework.data.jpa.repository.JpaRepository

interface ExperienceRepository: JpaRepository<Experience,Long> {

    fun  findByLabel(label:String): MutableList<Experience>
}