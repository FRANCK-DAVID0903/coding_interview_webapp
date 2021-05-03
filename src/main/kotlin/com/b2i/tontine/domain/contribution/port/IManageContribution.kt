package com.b2i.tontine.domain.contribution.port

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.contribution.entity.Contribution
import com.b2i.tontine.domain.region.entity.Region
import com.b2i.tontine.utils.OperationResult
import org.springframework.data.jpa.repository.Query

interface IManageContribution {

    fun findAllByUser(member: User): MutableList<Contribution>

    fun saveContribution(contribution: Contribution): OperationResult<Contribution>

    fun findAllByMonthNumber(month:Int): MutableList<Contribution>

    fun findAllByUserAndMonthNumber(member: User, month:Int): MutableList<Contribution>

}