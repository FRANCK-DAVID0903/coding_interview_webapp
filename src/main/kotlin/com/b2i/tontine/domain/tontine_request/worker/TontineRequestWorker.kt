package com.b2i.tontine.domain.tontine_request.worker

import com.b2i.tontine.application.controlForm.ControlForm
import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.entity.AssociationRole
import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.association_member.entity.AssociationMember
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine.entity.TontineType
import com.b2i.tontine.domain.tontine_request.entity.TontineRequest
import com.b2i.tontine.domain.tontine_request.port.TontineRequestDomain
import com.b2i.tontine.infrastructure.db.repository.AssociationMemberRepository
import com.b2i.tontine.infrastructure.db.repository.MemberRepository
import com.b2i.tontine.infrastructure.db.repository.TontineRepository
import com.b2i.tontine.infrastructure.db.repository.TontineRequestRepository
import com.b2i.tontine.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*


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

    @Autowired
    lateinit var associationMemberRepository: AssociationMemberRepository

    @Autowired
    lateinit var authenticationFacade: AuthenticationFacade

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
            val tontine = optionalTontine.get()
            val isMemberInTontine = tontineRequestRepository.findByTontineAndBeneficiary(tontine, optionalMember.get())

            if (isMemberInTontine.isPresent) {
                errors["error"] = "tontine_member_exist"
            } else {
                val tontineRequest = TontineRequest()
                tontineRequest.beneficiary = optionalMember.get()
                tontineRequest.tontine = tontine

                val requestDate = LocalDate.now()
                tontineRequest.requestDate = ControlForm.formatDate(requestDate.toString())

                //Trouver le statut du gars dans la tontine
                val user = authenticationFacade.getAuthenticatedUser()
                val memb = tontine.association?.let { associationMemberRepository.findByAssociationAndUser(it,user.get()) }

                val role = memb?.get()?.role

                if (role != AssociationRole.MEMBER){
                    tontineRequest.approved = true
                    tontine.numberOfParticipant += 1
                }

                if (tontine.type == TontineType.CLOSED) {
                    tontineRequest.approved = true

                    tontine.numberOfParticipant += 1
                    tontineRepository.save(tontine)
                }
                data = tontineRequestRepository.save(tontineRequest)
            }
        }
        return OperationResult(data, errors)
    }

    override fun approvedRequest(tontine_id: Long, member_id: Long): OperationResult<TontineRequest> {
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
            val tontine = optionalTontine.get()
            val memberRequest = tontineRequestRepository.findByTontineAndBeneficiary(tontine, optionalMember.get()).orElse(null)

            if (memberRequest == null) {
                errors["error"] = "tontine_member_not_exist"
            } else {
                memberRequest.approved = true
                tontine.numberOfParticipant += 1

                tontineRepository.save(tontine)
                data = tontineRequestRepository.save(memberRequest)
            }
        }
        return OperationResult(data, errors)
    }

    override fun unapprovedRequest(tontine_id: Long, member_id: Long): OperationResult<Boolean> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: Boolean? = null

        val optionalTontine = tontineRepository.findById(tontine_id)
        val optionalMember = memberRepository.findById(member_id)

        if (!optionalMember.isPresent) {
            errors["not_found"] = "user_not_found"
        }
        if (!optionalTontine.isPresent) {
            errors["not_found"] = "tontine_not_found"
        }

        if (errors.isEmpty()) {
            val tontine = optionalTontine.get()
            val memberRequest = tontineRequestRepository.findByTontineAndBeneficiary(tontine, optionalMember.get()).orElse(null)

            if (memberRequest == null) {
                errors["error"] = "tontine_member_not_exist"
            } else {
                if (tontine.numberOfParticipant > 0) {
                    tontine.numberOfParticipant -= 1
                    tontineRepository.save(tontine)
                }
                tontineRequestRepository.delete(memberRequest)
                data = true
            }
        }
        return OperationResult(data, errors)
    }

    override fun findAllApprovedTontineMembers(
        tontine: Tontine,
        validated: Boolean,
        state: Int
    ): MutableList<TontineRequest> {
        return tontineRequestRepository.findAllByTontineAndApprovedAndState(tontine, validated, state)
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

    override fun countAllByTontineAndApproved(tontine: Tontine, approved: Boolean): Long {
        return tontineRequestRepository.countAllByTontineAndApproved(tontine,approved)
    }
}