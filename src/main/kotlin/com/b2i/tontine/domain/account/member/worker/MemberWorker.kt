package com.b2i.tontine.domain.account.member.worker

import com.b2i.social.application.controlForm.ControlForm
import com.b2i.tontine.application.controlForm.Color
import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.account.member.port.MemberDomain
import com.b2i.tontine.infrastructure.db.repository.MemberRepository
import com.b2i.tontine.infrastructure.db.repository.UserRepository
import com.b2i.tontine.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:31
 */
@Component
class MemberWorker: MemberDomain {

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var userRepository: UserRepository

    override fun saveMember(member: Member): OperationResult<Member> {

        val errors :MutableMap<String,String> = mutableMapOf()
        var data:Member?=null

        if (userRepository.countAllByContactEmail(member.contact.email)>0L) {
            errors["email"]="String_email_always_used"
        } else if (userRepository.countAllByUsername(member.username)>0L) {
            errors["email"]="String_email_always_used"
        }

        if(errors.isEmpty()){
            data = memberRepository.saveAndFlush(member)
        }
        return OperationResult(data,errors)
    }
}