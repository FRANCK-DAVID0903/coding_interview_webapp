package com.b2i.tontine.domain.contribution.entity

import com.b2i.tontine.domain.entity.common.BaseEntity
import java.time.LocalDate


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:24
 */
class Contribution : BaseEntity() {

    var amount : Double = 0.0

    var contributionDate : LocalDate? = null

    // user
}