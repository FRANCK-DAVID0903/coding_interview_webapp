package com.b2i.tontine.domain.account.member.port

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.utils.OperationResult
import java.util.*

interface IManageMember {

    fun saveMember(member: Member):OperationResult<Member>

    fun updateInfosMember(member: Member):OperationResult<Member>

    fun updatePhoneAndEmail(id:Long, tel: String, email:String):OperationResult<Member>

    fun updatePassword(member: Member):OperationResult<Member>

    fun findById(id:Long): Optional<Member>

    fun findByContactEmail(email:String):Optional<Member>

    fun findByContactMobile(tel:String):Optional<Member>
}