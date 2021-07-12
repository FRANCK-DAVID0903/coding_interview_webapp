package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine_periodicity.entity.TontinePeriodicity
import com.b2i.tontine.utils.OperationResult
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TontinePeriodicityRepository:JpaRepository<TontinePeriodicity,Long> {

    fun findAllByPeriodicityStateAndTontine(state:String,tontine: Tontine): MutableList<TontinePeriodicity>

    fun countAllByBiddingState(state: String): Long

    fun countAllByTontine(tontine:Tontine):Long

    fun findAllByTontine(tontine: Tontine): MutableList<TontinePeriodicity>

    fun findByPeriodicityNumberAndTontine(number:Long,tontine: Tontine): Optional<TontinePeriodicity>

    fun findAllByBeneficiary(member: Member): MutableList<TontinePeriodicity>

    fun findByBeneficiaryAndTontine(member: Member,tontine: Tontine): Optional<TontinePeriodicity>

    fun findAllByBeneficiaryAndTontine_Association(member: Member,association: Association):MutableList<TontinePeriodicity>
}