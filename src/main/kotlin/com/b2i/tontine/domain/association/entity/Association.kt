package com.b2i.tontine.domain.association.entity

import com.b2i.tontine.domain.entity.common.BaseEntity
import javax.persistence.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:22
 */
@Entity
class Association : BaseEntity() {

    var name : String = ""

    var acronym : String = ""

    @Column(unique = true, nullable = false)
    var email : String = ""

    @Column(unique = true, nullable = false)
    var phoneNumber : String = ""

    @Column(columnDefinition = "text")
    var description : String = ""

    var state : Int = 0

}