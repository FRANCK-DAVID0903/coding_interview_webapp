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
import com.b2i.tontine.infrastructure.db.repository.TontineRequestRepository
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

    @Autowired
    lateinit var tontineRequestRepository: TontineRequestRepository

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

    override fun validateBidding(tontineBidding: TontineBidding): OperationResult<TontineBidding> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: TontineBidding? = null

        val periodicity = tontineBidding.tontinePeriodicity

        if (periodicity != null) {

            //Mettre a jour la periodicité
            periodicity.biddingState = TontineType.CLOSED
            periodicity.periodicityState = TontineType.CLOSED
            periodicity.beneficiary = tontineBidding.member
            tontinePeriodicityRepository.save(periodicity)

            //Mettre a jour la tontine request maintenant
                //On va trouver la ligne de la tontine request
            //val tontineRequest = tontineBidding.member?.let { tontineRequestRepository.findByTontinePeriodicityAndBeneficiaryAndApproved(periodicity, it,true) }

            val member = tontineBidding.member
            val tontine = periodicity.tontine

            var tontineRequest = member?.let {

                if (tontine != null) {

                    val tontineRequest = tontineRequestRepository.findByTontineAndBeneficiaryAndApproved(tontine,member,true)

                    if (tontineRequest == null){
                        errors["tontineRequest"] = "tontine_request_member_not_exist"
                    }
                    else{
                        val saveOb = tontineRequest.get()
                        saveOb.hasTaken = true
                        tontineRequestRepository.save(saveOb)
                    }
                }
            }

            data = tontineBiddingRepository.save(tontineBidding)

        }
        else {
            errors["not_found"] = "tontine_periodicity_not_found"
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

        val periodicity = tontineBidding.tontinePeriodicity

        if (optionalApproveBidding != null) {
            if (!optionalApproveBidding.isEmpty) {

                val bidding = optionalApproveBidding.get()

                bidding.biddingApproved = false
                tontineBidding.biddingApproved = true
                if (periodicity != null) {
                    periodicity.biddingAmount = tontineBidding.interestByValue
                    tontinePeriodicityRepository.save(periodicity)
                }

                tontineBiddingRepository.save(bidding)

                data = tontineBiddingRepository.save(tontineBidding)

            } else{
                tontineBidding.biddingApproved = true
                tontineBidding.biddingApproved = true
                if (periodicity != null) {
                    periodicity.biddingAmount = tontineBidding.interestByValue
                    tontinePeriodicityRepository.save(periodicity)
                }
                data = tontineBiddingRepository.save(tontineBidding)

            }
        }

        return OperationResult(data, errors)
    }
}