package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.account.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/14
 * @Time: 14:30
 */
interface MemberRepository : JpaRepository<Member, Long> {

    fun findByContactEmail(email:String):Optional<Member>

    fun findByContactMobile(tel:String):Optional<Member>
}