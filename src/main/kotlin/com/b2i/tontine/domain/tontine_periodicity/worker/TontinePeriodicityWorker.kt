package com.b2i.tontine.domain.tontine_periodicity.worker

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine_periodicity.entity.TontinePeriodicity
import com.b2i.tontine.domain.tontine_periodicity.port.TontinePeriodicityDomain
import com.b2i.tontine.infrastructure.db.repository.MemberRepository
import com.b2i.tontine.infrastructure.db.repository.TontinePeriodicityRepository
import com.b2i.tontine.infrastructure.db.repository.TontineRequestRepository
import com.b2i.tontine.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class TontinePeriodicityWorker: TontinePeriodicityDomain {

    @Autowired
    lateinit var tontinePeriodicityRepository: TontinePeriodicityRepository

    @Autowired
    lateinit var memberRepository: MemberRepository

    override fun saveTontinePeriodicity(tontinePeriodicity: TontinePeriodicity): OperationResult<TontinePeriodicity> {

            var errors: MutableMap<String, String> = mutableMapOf()
            var data: TontinePeriodicity? = null

            data = tontinePeriodicityRepository.save(tontinePeriodicity)

            return OperationResult(data,errors)
    }

    override fun countAllTontinePeriodicity(): Long {
        return tontinePeriodicityRepository.count()
    }

    override fun findAllByPeriodicityState(state: String): MutableList<TontinePeriodicity> {
        return tontinePeriodicityRepository.findAllByPeriodicityState(state)
    }

    override fun countAllByBiddingState(state: String): Long {
        return tontinePeriodicityRepository.countAllByBiddingState(state)
    }

    override fun countAllByTontine(tontine: Tontine): Long {
        return tontinePeriodicityRepository.countAllByTontine(tontine)
    }

    override fun findAllByTontine(tontine: Tontine): MutableList<TontinePeriodicity> {
        return tontinePeriodicityRepository.findAllByTontine(tontine)
    }

    override fun addBeneficiary(member: Member, id: Long):OperationResult<TontinePeriodicity> {
        var errors: MutableMap<String, String> = mutableMapOf()
        var data: TontinePeriodicity? = null

        //Search the tontinePeriodicity
        val tontinePeriodicitySearch = tontinePeriodicityRepository.findById(id).orElse(null)

        if (tontinePeriodicitySearch != null){
            tontinePeriodicitySearch.beneficiary = member
            data = tontinePeriodicityRepository.save(tontinePeriodicitySearch)
        }
        else{
            errors["tontinePeriodicity"] = "Periodicity_not_found"
        }

        return OperationResult(data,errors)
    }

    override fun findById(id: Long): Optional<TontinePeriodicity> {
        return tontinePeriodicityRepository.findById(id)
    }

    override fun updateTontine(tontinePeriodicity: TontinePeriodicity): OperationResult<TontinePeriodicity> {

        var errors: MutableMap<String, String> = mutableMapOf()
        var data= tontinePeriodicity


        if (tontinePeriodicity.contributionStartDate!!.before(tontinePeriodicity.contributionEndDate)){

            /*if (tontinePeriodicity.contributionEndDate!!.before(tontinePeriodicity.biddingDeadline)){
                data = tontinePeriodicityRepository.save(tontinePeriodicity)
            }
            else{
                errors["date"] = "dateTake_inf_dateBegin"
            }*/
            data = tontinePeriodicityRepository.save(tontinePeriodicity)
        }
        else{
            errors["date"] = "dateEnd_inf_dateBegin"
        }

        return OperationResult(data,errors)

    }

    override fun findByPeriodicityNumberAndTontine(number: Long, tontine: Tontine): Optional<TontinePeriodicity> {
        return tontinePeriodicityRepository.findByPeriodicityNumberAndTontine(number,tontine)
    }

    override fun findAllByBeneficiary(member: Member): MutableList<TontinePeriodicity> {
        return tontinePeriodicityRepository.findAllByBeneficiary(member)
    }

    override fun findByBeneficiaryAndTontine(member: Member, tontine: Tontine): Optional<TontinePeriodicity> {
        return tontinePeriodicityRepository.findByBeneficiaryAndTontine(member,tontine)
    }

    override fun findAllByBeneficiaryAndTontine_Association(member: Member, association: Association): MutableList<TontinePeriodicity> {
        return tontinePeriodicityRepository.findAllByBeneficiaryAndTontine_Association(member,association)
    }
}