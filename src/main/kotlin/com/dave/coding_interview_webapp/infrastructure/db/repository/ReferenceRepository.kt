package com.dave.coding_interview_webapp.infrastructure.db.repository

import com.dave.coding_interview_webapp.domain.reference.entity.Reference
import org.springframework.data.jpa.repository.JpaRepository

interface ReferenceRepository: JpaRepository<Reference,Long> {
}