package com.b2i.tontine.domain.account.member.entity

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.account.entity.UserType
import java.util.*
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:30
 */
@Entity
@DiscriminatorValue(UserType.ASSOCIATION_MEMBER)
class Member() : User() {

    var photo : String= ""

    var birthday : Date = Date(2000, 1, 1)
}