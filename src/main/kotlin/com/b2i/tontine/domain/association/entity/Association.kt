package com.b2i.tontine.domain.association.entity

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.account.entity.UserType
import javax.persistence.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:22
 */
@Entity
@DiscriminatorValue(UserType.ASSOCIATION_ADMIN)
class Association() : User(){

    var name : String = ""

    var acronym : String = ""

    @Column(columnDefinition = "text")
    var description : String = ""

    var state : Int = 0

}