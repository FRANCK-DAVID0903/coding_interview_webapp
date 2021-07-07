package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.tontine_bidding.entity.TontineBidding
import com.b2i.tontine.domain.tontine_periodicity.entity.TontinePeriodicity
import com.b2i.tontine.utils.OperationResult
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/14
 * @Time: 14:24
 */
interface TontineBiddingRepository : JpaRepository<TontineBidding, Long> {

    fun findAllByTontinePeriodicity(tontinePeriodicity: TontinePeriodicity): MutableList<TontineBidding>

    fun findAllByTontinePeriodicityAndMember(tontinePeriodicity: TontinePeriodicity, member: Member): MutableList<TontineBidding>

    fun countAllByTontinePeriodicityAndBiddingApproved(tontinePeriodicity: TontinePeriodicity,state:Boolean): Long

    fun findByTontinePeriodicityAndBiddingApproved(tontinePeriodicity: TontinePeriodicity,state:Boolean): Optional<TontineBidding>

    fun findByTontinePeriodicityAndMember(tontinePeriodicity: TontinePeriodicity,member: Member) : Optional<TontineBidding>

}