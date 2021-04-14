package com.b2i.tontine.domain.tontine.entity

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.entity.common.BaseEntity
import com.fasterxml.jackson.annotation.JsonBackReference
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:40
 */
@Entity
class Tontine : BaseEntity() {

    var name : String = ""

    var contributionAmount : Double = 0.0

    var tontineGlobalAmount : Double = 0.0

    var numberOfParticipant : Int = 0

    var periodicity : String = ""

    var startDate : LocalDate? = null

    var endDate : LocalDate? = null

    var biddingAmount : Double = 0.0

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var beneficiary : Member? = null

    @ManyToOne(targetEntity = Association::class, optional = false)
    var association : Association? = null

    var isClosed : Boolean = false

    var state : Int = 0

}