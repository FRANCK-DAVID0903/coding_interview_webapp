package com.b2i.tontine.domain.tontine.entity

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.entity.common.BaseEntity
import com.fasterxml.jackson.annotation.JsonBackReference
import java.time.LocalDate
import java.util.*
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

    var tontineGlobalAmountEstimated : Double = 0.0

    var numberOfParticipant : Long = 0

    var numberOfParticipantEstimated : Long = 0

    var periodicity : String = TontinePeriodicity.MONTHLY

    var type : String = TontineType.OPENED

    var startDate : Date? = null

    var endDate : Date? = null

    var biddingAmount : Double = 0.0

    var membershipState : String = TontineType.CLOSED

    var membershipDeadline : Date? = null

    @ManyToOne(targetEntity = Association::class, optional = false)
    var association : Association? = null

    var isClosed : Boolean = false

    var state : Int = 0

}