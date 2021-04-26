package com.b2i.tontine.domain.tontine_request.worker

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.association_member.entity.AssociationMember
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine.entity.TontineType
import com.b2i.tontine.domain.tontine_request.entity.TontineRequest
import com.b2i.tontine.domain.tontine_request.port.TontineRequestDomain
import com.b2i.tontine.infrastructure.db.repository.MemberRepository
import com.b2i.tontine.infrastructure.db.repository.TontineRepository
import com.b2i.tontine.infrastructure.db.repository.TontineRequestRepository
import com.b2i.tontine.utils.OperationResult
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

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var tontineRepository: TontineRepository

    override fun createTontineRequest(tontine_id: Long, member_id: Long): OperationResult<TontineRequest> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: TontineRequest? = null

        val optionalTontine = tontineRepository.findById(tontine_id)
        val optionalMember = memberRepository.findById(member_id)

        if (!optionalMember.isPresent) {
            errors["not_found"] = "user_not_found"
        }
        if (!optionalTontine.isPresent) {
            errors["not_found"] = "tontine_not_found"
        }

        if (errors.isEmpty()) {
            val isMemberInTontine = tontineRequestRepository.findByTontineAndBeneficiary(optionalTontine.get(), optionalMember.get())

            if (isMemberInTontine.isPresent) {
                errors["error"] = "tontine_member_exist"
            } else {
                val tontineRequest = TontineRequest()
                tontineRequest.beneficiary = optionalMember.get()
                tontineRequest.tontine = optionalTontine.get()

                if (optionalTontine.get().type == TontineType.OPENED) {
                    tontineRequest.approved = true
                }

                data = tontineRequestRepository.save(tontineRequest)
            }
        }

        return OperationResult(data, errors)
    }

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

    override fun findAllByTontineAndStatus(tontine: Tontine, validated: Boolean): MutableList<TontineRequest> {
        return tontineRequestRepository.findAllByTontineAndApproved(tontine, validated)
    }

    override fun countAllByTontineAndState(tontine: Tontine, state: Int): Long {
        return tontineRequestRepository.countAllByTontineAndState(tontine,state)
    }
}