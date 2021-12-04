package com.dave.coding_interview_webapp.infrastructure.db.repository

import com.dave.coding_interview_webapp.domain.formation.entity.Formation
import org.springframework.data.jpa.repository.JpaRepository

interface FormationRepository: JpaRepository<Formation,Long> {

}