package com.b2i.tontine.domain.tontine.entity

import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.entity.common.BaseEntity
import java.time.LocalDate
import javax.persistence.ManyToOne


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:40
 */
class Tontine : BaseEntity() {

    var name : String = ""

    var contributionAmount : Double = 0.0

    var tontineGlobalAmount : Double = 0.0

    var numberOfParticipant : Int = 0

    var periodicity : String = ""

    var startDate : LocalDate? = null

    var endDate : LocalDate? = null

    @ManyToOne(targetEntity = Association::class, optional = false)
    var association : Association? = null

    var state : Int = 0

}