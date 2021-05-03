package com.b2i.tontine.domain.contribution.entity

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.entity.common.BaseEntity
import com.fasterxml.jackson.annotation.JsonBackReference
import java.time.LocalDate
import java.util.*
import javax.persistence.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:24
 */
@Entity
class Contribution : BaseEntity() {

    var amount : Double = 0.0

    var contributionDate : Date? = null

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "association_id")
    var association : Association? = null

    var monthNumber : Int = 0

    @ManyToOne(targetEntity = User::class, optional = false)
    var user : User? = null

    var state : Int = 0

    // user
}