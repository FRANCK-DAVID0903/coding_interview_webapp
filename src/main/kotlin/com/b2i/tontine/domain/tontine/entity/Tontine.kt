package com.b2i.tontine.domain.tontine.entity

import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.entity.common.BaseEntity
import java.util.*
import javax.persistence.Entity
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

    var periodicity : String = TontinePeriodicityType.MONTHLY

    var type : String = TontineType.OPENED

    var startDate : Date? = null

    var endDate : Date? = null

    var openToMembership: Boolean = false

    var membershipDeadline : Date? = null

    @ManyToOne(targetEntity = Association::class, optional = false)
    var association : Association? = null

    var isClosed : Boolean = false

    var state : Int = 0

}