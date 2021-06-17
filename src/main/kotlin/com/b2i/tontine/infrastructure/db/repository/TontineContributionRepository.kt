package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine_contribution.entity.TontineContribution
import com.b2i.tontine.domain.tontine_periodicity.entity.TontinePeriodicity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TontineContributionRepository: JpaRepository<TontineContribution,Long> {

    fun findAllByMemberAndTontine(member: Member,tontine:Tontine):MutableList<TontineContribution>

    fun findAllByTontinePeriodicity(tontinePeriodicity: TontinePeriodicity): MutableList<TontineContribution>

    fun findByTontinePeriodicityAndMemberAndTontine(tontinePeriodicity: TontinePeriodicity,member: Member, tontine: Tontine): Optional<TontineContribution>

    fun findAllByMemberAndContributed(member: Member,contributed:Boolean):MutableList<TontineContribution>


}