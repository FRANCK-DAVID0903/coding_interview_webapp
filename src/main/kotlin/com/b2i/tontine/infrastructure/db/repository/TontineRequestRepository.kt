package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.tontine_request.entity.TontineRequest
import org.springframework.data.jpa.repository.JpaRepository


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/14
 * @Time: 15:59
 */
interface TontineRequestRepository : JpaRepository<TontineRequest, Long> {
}