package com.b2i.tontine.domain.region.entity

import com.b2i.tontine.domain.entity.common.BaseEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/14
 * @Time: 14:00
 */
@Entity
class Region : BaseEntity() {

    @JsonIgnore
    var label : String = ""

    @JsonIgnore
    var description : String = ""

}