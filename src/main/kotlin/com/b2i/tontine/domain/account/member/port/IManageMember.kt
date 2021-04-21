package com.b2i.tontine.domain.account.member.port

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.utils.OperationResult
import java.util.*

interface IManageMember {

    fun saveMember(member: Member):OperationResult<Member>

    fun updateInfosMember(member: Member):OperationResult<Member>

    fun findById(id:Long): Optional<Member>
}