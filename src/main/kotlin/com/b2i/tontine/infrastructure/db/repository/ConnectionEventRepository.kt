package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.entity.common.ConnectionEvent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConnectionEventRepository : JpaRepository<ConnectionEvent, Long> {

    fun countAllByUser_Id(id: Long): Long
}
