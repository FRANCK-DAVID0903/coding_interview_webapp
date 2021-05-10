package com.b2i.tontine.domain.tontine_bidding.entity

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.entity.common.BaseEntity
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine_periodicity.entity.TontinePeriodicity
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

    var interestByPercentage : Int = 0

    var interestByValue : Double = 0.0

    var biddingApproved : Boolean = false

    var firstBidding : Boolean = false

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member : Member? = null

    @ManyToOne(targetEntity = TontinePeriodicity::class, optional = false)
    var tontinePeriodicity: TontinePeriodicity? = null

    var state : Int = 0
}