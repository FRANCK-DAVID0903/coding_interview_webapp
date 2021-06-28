package com.b2i.tontine.domain.tontine_contribution.port

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine_contribution.entity.TontineContribution
import com.b2i.tontine.domain.tontine_periodicity.entity.TontinePeriodicity
import com.b2i.tontine.utils.OperationResult
import java.util.*

interface IManageTontineContribution {

    fun saveContribution(tontineContribution: TontineContribution):OperationResult<TontineContribution>

    fun findById(id:Long): Optional<TontineContribution>

    fun findAllByMemberAndTontine(member: Member, tontine: Tontine):MutableList<TontineContribution>

    fun findAllByTontinePeriodicity(tontinePeriodicity: TontinePeriodicity): MutableList<TontineContribution>

    fun findByTontinePeriodicityAndMemberAndTontine(tontinePeriodicity: TontinePeriodicity,member: Member, tontine: Tontine): Optional<TontineContribution>

    fun findAllByMemberAndContributed(member: Member,contributed:Boolean):MutableList<TontineContribution>

    fun findAllByMemberAndContributedAndTontine_Association(member: Member,contributed: Boolean,association: Association): MutableList<TontineContribution>

}