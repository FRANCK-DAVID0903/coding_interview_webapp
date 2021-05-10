package com.b2i.tontine.domain.tontine_contribution.worker

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine_contribution.entity.TontineContribution
import com.b2i.tontine.domain.tontine_contribution.port.TontineContributionDomain
import com.b2i.tontine.domain.tontine_periodicity.entity.TontinePeriodicity
import com.b2i.tontine.infrastructure.db.repository.TontineContributionRepository
import com.b2i.tontine.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class TontineContributionWorker:TontineContributionDomain {

    @Autowired
    lateinit var tontineContributionRepository: TontineContributionRepository

    override fun saveContribution(tontineContribution: TontineContribution): OperationResult<TontineContribution> {
        var errors: MutableMap<String, String> = mutableMapOf()
        var data: TontineContribution? = null

        data = tontineContributionRepository.save(tontineContribution)

        return OperationResult(data,errors)
    }

    override fun findById(id: Long): Optional<TontineContribution> {
        return tontineContributionRepository.findById(id)
    }

    override fun findAllByMemberAndTontine(member: Member, tontine: Tontine): MutableList<TontineContribution> {
        return tontineContributionRepository.findAllByMemberAndTontine(member,tontine)
    }
}