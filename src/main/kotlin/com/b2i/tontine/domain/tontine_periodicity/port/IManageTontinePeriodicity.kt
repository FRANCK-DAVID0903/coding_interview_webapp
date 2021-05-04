package com.b2i.tontine.domain.tontine_periodicity.port

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine_periodicity.entity.TontinePeriodicity
import com.b2i.tontine.utils.OperationResult
import java.util.*

interface IManageTontinePeriodicity {

    fun saveTontinePeriodicity(tontinePeriodicity: TontinePeriodicity): OperationResult<TontinePeriodicity>

    fun countAllTontinePeriodicity():Long

    fun findAllByPeriodicityState(state:String): MutableList<TontinePeriodicity>

    fun countAllByBiddingState(state: String): Long

    fun countAllByTontine(tontine: Tontine):Long

    fun findAllByTontine(tontine: Tontine): MutableList<TontinePeriodicity>

    fun addBeneficiary(member: Member,id:Long): OperationResult<TontinePeriodicity>

    fun findById(id: Long):Optional<TontinePeriodicity>

    fun updateTontine(tontinePeriodicity: TontinePeriodicity):OperationResult<TontinePeriodicity>
}