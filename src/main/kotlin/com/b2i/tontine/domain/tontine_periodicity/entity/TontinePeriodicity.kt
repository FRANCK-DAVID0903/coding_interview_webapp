package com.b2i.tontine.domain.tontine_periodicity.entity

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.entity.common.BaseEntity
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine.entity.TontineType
import com.fasterxml.jackson.annotation.JsonBackReference
import java.util.*
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/27
 * @Time: 11:53
 */
@Entity
class TontinePeriodicity: BaseEntity() {

    var periodicityNumber: Long = 0

    var periodicityState : String = TontineType.CLOSED

    var contributionStartDate: Date? = null

    var contributionEndDate: Date? = null

    var biddingState: String = TontineType.CLOSED

    var biddingDeadline : Date? = null

    var biddingAmount : Double = 0.0

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var beneficiary : Member? = null

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tontine_id")
    var tontine : Tontine? = null

}