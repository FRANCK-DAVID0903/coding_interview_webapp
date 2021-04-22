package com.b2i.tontine.domain.account.member.worker

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.account.member.port.MemberDomain
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.infrastructure.db.repository.MemberRepository
import com.b2i.tontine.infrastructure.db.repository.UserRepository
import com.b2i.tontine.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*


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

    override fun updateInfosMember(member: Member): OperationResult<Member> {

        val errors :MutableMap<String,String> = mutableMapOf()
        var data:Member?=null

        if(errors.isEmpty()){
            data = memberRepository.save(member)
        }
        return OperationResult(data,errors)
    }

    override fun updatePhoneAndEmail(id:Long, tel: String, email: String): OperationResult<Member> {

        val errors: MutableMap<String, String> = mutableMapOf()
        var data: Member? = null

        if (tel.isEmpty()) {
            errors["tel"] = "user_phone_empty"
        }
        if (email.isEmpty()) {
            errors["email"] = "user_email_empty"
        }

        if (errors.isEmpty()){

            val member = memberRepository.findById(id)

            if (!member.isPresent){
                errors["member"] = "user_not_found"
            }else{
                val memberSave = member.get()
                val optionalMemberTel = memberRepository.findByContactMobile(tel)

                if (optionalMemberTel.isPresent){

                    val memberPhone = optionalMemberTel.get()
                    if (memberPhone.id == memberSave.id){
                        memberSave.contact.mobile = tel

                        val optionalMemberEmail = memberRepository.findByContactEmail(email)
                        if (optionalMemberEmail.isPresent){
                            val memberEmail = optionalMemberEmail.get()
                            if (memberEmail.id == memberSave.id){
                                memberSave.contact.email = email
                            }
                            else {
                                errors["phone"] = "user_phoneNumber_already_exist"
                            }
                        }

                    }
                    else {
                        errors["phone"] = "user_phoneNumber_already_exist"
                    }

                }

                data = memberRepository.save(memberSave)
            }

        }

        return  OperationResult(data, errors)
    }

    override fun updatePassword(pswd: String): OperationResult<Member> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Optional<Member> {
        return memberRepository.findById(id)
    }

    override fun findByContactEmail(email: String): Optional<Member> {
        return memberRepository.findByContactEmail(email)
    }

    override fun findByContactMobile(tel: String): Optional<Member> {
        return memberRepository.findByContactMobile(tel)
    }

}