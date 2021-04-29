package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.tontine_periodicity.entity.TontinePeriodicity
import org.springframework.data.jpa.repository.JpaRepository

interface TontinePeriodicityRepository:JpaRepository<TontinePeriodicity,Long> {

    fun findAllByPeriodicityState(state:String): MutableList<TontinePeriodicity>

    fun countAllByBiddingState(state: String): Long
}