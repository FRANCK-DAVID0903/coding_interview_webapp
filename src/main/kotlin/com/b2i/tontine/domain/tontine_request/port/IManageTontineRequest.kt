package com.b2i.tontine.domain.tontine_request.port

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine_request.entity.TontineRequest

interface IManageTontineRequest {

    fun findAllByBeneficiary(member: Member): MutableList<TontineRequest>

    fun countAllByBeneficiary(member: Member): Long

    fun findAllByBeneficiaryAndState(member: Member, state: Int): MutableList<TontineRequest>

    fun countAllByBeneficiaryAndState(member: Member, state: Int) : Long

    fun findAllByTontine(tontine: Tontine): MutableList<TontineRequest>

    fun countAllByTontine(tontine: Tontine): Long

    fun findAllByTontineAndState(tontine: Tontine, state: Int):MutableList<TontineRequest>

    fun countAllByTontineAndState(tontine: Tontine, state: Int) : Long
}