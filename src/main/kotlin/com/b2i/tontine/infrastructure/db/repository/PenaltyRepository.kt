package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.penalty.entity.Penalty
import org.springframework.data.jpa.repository.JpaRepository


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/14
 * @Time: 14:22
 */
interface PenaltyRepository : JpaRepository<Penalty, Long> {
}