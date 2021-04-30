package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine_request.entity.TontineRequest
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/14
 * @Time: 15:59
 */
interface TontineRequestRepository : JpaRepository<TontineRequest, Long> {

    fun findByTontineAndBeneficiary(tontine: Tontine, beneficiary: Member): Optional<TontineRequest>

    fun findAllByBeneficiary(member:Member): MutableList<TontineRequest>

    fun countAllByBeneficiary(member: Member): Long

    fun findAllByBeneficiaryAndState(member: Member,state: Int): MutableList<TontineRequest>

    fun countAllByBeneficiaryAndState(member: Member,state: Int) : Long

    fun findAllByTontine(tontine:Tontine): MutableList<TontineRequest>

    fun countAllByTontine(tontine: Tontine): Long

    fun findAllByTontineAndState(tontine: Tontine,state: Int):MutableList<TontineRequest>

    fun findAllByTontineAndApproved(tontine: Tontine, approved: Boolean): MutableList<TontineRequest>

    fun findAllByTontineAndApprovedAndState(tontine: Tontine, approved: Boolean, state: Int): MutableList<TontineRequest>

    fun countAllByTontineAndState(tontine: Tontine, state: Int) : Long
}