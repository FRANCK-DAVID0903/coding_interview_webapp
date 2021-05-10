package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine_periodicity.entity.TontinePeriodicity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TontinePeriodicityRepository:JpaRepository<TontinePeriodicity,Long> {

    fun findAllByPeriodicityState(state:String): MutableList<TontinePeriodicity>

    fun countAllByBiddingState(state: String): Long

    fun countAllByTontine(tontine:Tontine):Long

    fun findAllByTontine(tontine: Tontine): MutableList<TontinePeriodicity>
}