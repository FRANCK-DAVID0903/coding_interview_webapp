package com.b2i.tontine.domain.bidding.entity

import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.entity.common.BaseEntity
import com.b2i.tontine.domain.tontine.entity.Tontine
import java.time.LocalDate
import javax.persistence.ManyToOne


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:23
 */
class Bidding : BaseEntity() {

    var amount : Double = 0.0

    var interestByPercentage : Int = 0

    var interestByValue : Int = 0

    var reimbursementDeadline : LocalDate? = null

    @ManyToOne(targetEntity = Tontine::class, optional = false)
    var tontine : Tontine? = null

    // tontine foreignkey

    // beneficiary foreingkey

    var state : Int = 0
}