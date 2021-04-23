package com.b2i.tontine.domain.tontine_request.worker

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine_request.entity.TontineRequest
import com.b2i.tontine.domain.tontine_request.port.TontineRequestDomain
import com.b2i.tontine.infrastructure.db.repository.TontineRequestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/14
 * @Time: 15:47
 */
@Service
class TontineRequestWorker:TontineRequestDomain {

    @Autowired
    lateinit var tontineRequestRepository: TontineRequestRepository

    override fun findAllByBeneficiary(member: Member): MutableList<TontineRequest> {
        return tontineRequestRepository.findAllByBeneficiary(member)
    }

    override fun countAllByBeneficiary(member: Member): Long {
        return tontineRequestRepository.countAllByBeneficiary(member)
    }

    override fun findAllByBeneficiaryAndState(member: Member, state: Int): MutableList<TontineRequest> {
        return tontineRequestRepository.findAllByBeneficiaryAndState(member,state)
    }

    override fun countAllByBeneficiaryAndState(member: Member, state: Int): Long {
        return tontineRequestRepository.countAllByBeneficiaryAndState(member,state)
    }

    override fun findAllByTontine(tontine: Tontine): MutableList<TontineRequest> {
        return tontineRequestRepository.findAllByTontine(tontine)
    }

    override fun countAllByTontine(tontine: Tontine): Long {
        return tontineRequestRepository.countAllByTontine(tontine)
    }

    override fun findAllByTontineAndState(tontine: Tontine, state: Int): MutableList<TontineRequest> {
        return tontineRequestRepository.findAllByTontineAndState(tontine,state)
    }

    override fun countAllByTontineAndState(tontine: Tontine, state: Int): Long {
        return tontineRequestRepository.countAllByTontineAndState(tontine,state)
    }
}