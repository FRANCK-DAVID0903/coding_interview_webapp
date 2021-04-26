package com.b2i.tontine.domain.tontine_bidding.entity

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.entity.common.BaseEntity
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.fasterxml.jackson.annotation.JsonBackReference
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:23
 */
@Entity
class TontineBidding : BaseEntity() {

    var amount : Double = 0.0

    var interestByPercentage : Int = 0

    var interestByValue : Int = 0

    var reimbursementDeadline : LocalDate? = null

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member : Member? = null

    @ManyToOne(targetEntity = Tontine::class, optional = false)
    var tontine : Tontine? = null

    var state : Int = 0
}