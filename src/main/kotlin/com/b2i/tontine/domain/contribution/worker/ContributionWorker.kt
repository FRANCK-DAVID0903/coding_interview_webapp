package com.b2i.tontine.domain.contribution.worker

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.contribution.entity.Contribution
import com.b2i.tontine.domain.contribution.port.ContributionDomain
import com.b2i.tontine.domain.region.entity.Region
import com.b2i.tontine.infrastructure.db.repository.ContributionRepository
import com.b2i.tontine.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:24
 */
@Service
class ContributionWorker: ContributionDomain {

    @Autowired
    lateinit var contributionRepository: ContributionRepository

    override fun findAllByUser(member: User): MutableList<Contribution> {
        return contributionRepository.findAllByUser(member)
    }

    override fun saveContribution(contribution: Contribution): OperationResult<Contribution> {
        var errors : MutableMap<String,String> = mutableMapOf()
        var data : Contribution? = null

        if (errors.isEmpty()){
            data  = contributionRepository.save(contribution)
        }

        return OperationResult(data,errors)
    }

    override fun findAllByMonthNumber(month: Int): MutableList<Contribution> {
        return contributionRepository.findAllByMonthNumber(month)
    }

    override fun findAllByUserAndMonthNumber(member: User, month: Int): MutableList<Contribution> {
        return contributionRepository.findAllByUserAndMonthNumber(member,month)
    }

}