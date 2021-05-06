package com.b2i.tontine.domain.tontine_bidding.port

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.tontine_bidding.entity.TontineBidding
import com.b2i.tontine.domain.tontine_periodicity.entity.TontinePeriodicity
import com.b2i.tontine.utils.OperationResult
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/05/05
 * @Time: 12:51
 */
interface IManageTontineBidding {

    fun makeBidding(tontineBidding: TontineBidding, periodicity_id: Long): OperationResult<TontineBidding>

    fun validateBidding(tontineBidding_id: Long): OperationResult<TontineBidding>

    fun findBiddingById(bidding_id: Long): Optional<TontineBidding>

    fun findAllBiddingByPeriodicity(tontinePeriodicity: TontinePeriodicity): MutableList<TontineBidding>

    fun findAllBiddingByPeriodicityAndMember(tontinePeriodicity: TontinePeriodicity, member: Member): MutableList<TontineBidding>

}