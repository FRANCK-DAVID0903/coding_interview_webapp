package com.b2i.tontine.domain.association.entity

import com.b2i.tontine.domain.entity.common.BaseEntity
import javax.persistence.Entity


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:22
 */
@Entity
class Association : BaseEntity() {

    var name : String = ""

    var description : String = ""

    var state : Int = 0

}