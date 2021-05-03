package com.b2i.tontine.domain.tontine_request.port

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine_request.entity.TontineRequest
import com.b2i.tontine.utils.OperationResult

interface IManageTontineRequest {

    fun createTontineRequest(tontine_id: Long, member_id: Long): OperationResult<TontineRequest>

    fun approvedRequest(tontine_id: Long, member_id: Long): OperationResult<TontineRequest>

    fun unapprovedRequest(tontine_id: Long, member_id: Long): OperationResult<Boolean>

    fun findAllApprovedTontineMembers(tontine: Tontine, validated: Boolean, state: Int): MutableList<TontineRequest>

    fun findAllByBeneficiary(member: Member): MutableList<TontineRequest>

    fun countAllByBeneficiary(member: Member): Long

    fun findAllByBeneficiaryAndState(member: Member, state: Int): MutableList<TontineRequest>

    fun countAllByBeneficiaryAndState(member: Member, state: Int) : Long

    fun findAllByTontine(tontine: Tontine): MutableList<TontineRequest>

    fun countAllByTontine(tontine: Tontine): Long

    fun findAllByTontineAndState(tontine: Tontine, state: Int):MutableList<TontineRequest>

    fun findAllByTontineAndStatus(tontine: Tontine, validated: Boolean): MutableList<TontineRequest>

    fun countAllByTontineAndState(tontine: Tontine, state: Int) : Long

    fun countAllByTontineAndApproved(tontine: Tontine, approved: Boolean): Long
}