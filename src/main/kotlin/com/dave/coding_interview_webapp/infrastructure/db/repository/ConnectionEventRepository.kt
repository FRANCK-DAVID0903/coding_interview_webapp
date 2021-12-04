package com.dave.coding_interview_webapp.infrastructure.db.repository

import com.dave.coding_interview_webapp.domain.entity.common.ConnectionEvent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConnectionEventRepository : JpaRepository<ConnectionEvent, Long> {

    fun countAllByUser_Id(id: Long): Long
}
