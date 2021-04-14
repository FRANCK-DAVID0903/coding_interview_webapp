package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.tontine_contribution.entity.TontineContribution
import org.springframework.data.jpa.repository.JpaRepository


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/14
 * @Time: 14:14
 */
interface TontineContributionRepository : JpaRepository<TontineContribution, Long> {
}