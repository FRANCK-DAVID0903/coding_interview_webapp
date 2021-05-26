package com.b2i.tontine.domain.tontine_bidding.worker

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine.entity.TontineType
import com.b2i.tontine.domain.tontine_bidding.entity.TontineBidding
import com.b2i.tontine.domain.tontine_bidding.port.TontineBiddingDomain
import com.b2i.tontine.domain.tontine_periodicity.entity.TontinePeriodicity
import com.b2i.tontine.infrastructure.db.repository.MemberRepository
import com.b2i.tontine.infrastructure.db.repository.TontineBiddingRepository
import com.b2i.tontine.infrastructure.db.repository.TontinePeriodicityRepository
import com.b2i.tontine.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:23
 */
@Service
class TontineBiddingWorker : TontineBiddingDomain {

    @Autowired
    lateinit var tontineBiddingRepository: TontineBiddingRepository

    @Autowired
    lateinit var tontinePeriodicityRepository: TontinePeriodicityRepository

    @Autowired
    lateinit var memberRepository: MemberRepository

    override fun makeBidding(tontineBidding: TontineBidding, periodicity_id: Long): OperationResult<TontineBidding> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: TontineBidding? = null

        val optionalPeriodicity = tontinePeriodicityRepository.findByIdOrNull(periodicity_id)
        if (optionalPeriodicity == null) {
            errors["not_found"] = "tontine_periodicity_not_found"
        }

        if (tontineBidding.interestByValue < 0.0) {
            errors["contributionAmount"] = "tontine_contribution_amount_null"
        }

        if (errors.isEmpty()) {
            tontineBidding.tontinePeriodicity = optionalPeriodicity

            if (optionalPeriodicity!!.biddingState == TontineType.OPENED) {
                data = tontineBiddingRepository.save(tontineBidding)
            } else {
                errors["tontine_bidding_impossible"] = "tontine_bidding_impossible"
            }
        }

        return OperationResult(data, errors)
    }

    override fun validateBidding(tontineBidding_id: Long): OperationResult<TontineBidding> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: TontineBidding? = null

        val optionalBidding = tontineBiddingRepository.findById(tontineBidding_id)

        if (!optionalBidding.isPresent) {
            errors["not_found"] = "tontine_bidding_not_found"
        } else {
            val bidding = optionalBidding.get()

            val optionalPeriodicity = tontinePeriodicityRepository.findByIdOrNull(bidding.tontinePeriodicity!!.id)
            if (optionalPeriodicity != null) {
                optionalPeriodicity.biddingAmount = bidding.interestByValue
                optionalPeriodicity.beneficiary = bidding.member
                tontinePeriodicityRepository.save(optionalPeriodicity)
            }

            data = tontineBiddingRepository.findByIdOrNull(bidding.id)
        }

        return OperationResult(data, errors)
    }

    override fun findBiddingById(bidding_id: Long): Optional<TontineBidding> =
        tontineBiddingRepository.findById(bidding_id)

    override fun findAllBiddingByPeriodicity(tontinePeriodicity: TontinePeriodicity): MutableList<TontineBidding> =
        tontineBiddingRepository.findAllByTontinePeriodicity(tontinePeriodicity)

    override fun findAllBiddingByPeriodicityAndMember(
        tontinePeriodicity: TontinePeriodicity,
        member: Member
    ): MutableList<TontineBidding> =
        tontineBiddingRepository.findAllByTontinePeriodicityAndMember(tontinePeriodicity, member)

    override fun countAllByTontinePeriodicityAndBiddingApproved(tontinePeriodicity: TontinePeriodicity, state: Boolean): Long {
        return tontineBiddingRepository.countAllByTontinePeriodicityAndBiddingApproved(tontinePeriodicity,state)
    }

    override fun findByTontinePeriodicityAndBiddingApproved(tontinePeriodicity: TontinePeriodicity, state: Boolean): Optional<TontineBidding> {
        return tontineBiddingRepository.findByTontinePeriodicityAndBiddingApproved(tontinePeriodicity,state)
    }

    override fun apprroveBidding(tontineBidding: TontineBidding): OperationResult<TontineBidding> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: TontineBidding? = null

        val optionalApproveBidding = tontineBidding?.tontinePeriodicity?.let { tontineBiddingRepository.findByTontinePeriodicityAndBiddingApproved(it,true) }

        if (optionalApproveBidding != null) {

            if (!optionalApproveBidding.isPresent) {
                tontineBidding.biddingApproved = true
            } else {
                val bidding = optionalApproveBidding.get()

                bidding.biddingApproved = false
                tontineBidding.biddingApproved = true

                tontineBiddingRepository.save(bidding)

                data = tontineBiddingRepository.save(tontineBidding)
            }
        }

        return OperationResult(data, errors)
    }
}