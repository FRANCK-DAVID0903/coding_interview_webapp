package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.bidding.entity.Bidding
import org.springframework.data.jpa.repository.JpaRepository


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/14
 * @Time: 14:24
 */
interface BiddingRepository : JpaRepository<Bidding, Long> {
}