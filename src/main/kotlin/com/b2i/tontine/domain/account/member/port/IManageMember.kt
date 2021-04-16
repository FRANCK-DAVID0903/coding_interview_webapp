package com.b2i.tontine.domain.account.member.port

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.utils.OperationResult

interface IManageMember {

    fun saveMember(member: Member):OperationResult<Member>
}