package com.dave.coding_interview_webapp.domain.reference.entity

import com.dave.coding_interview_webapp.domain.entity.common.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Reference: BaseEntity() {

    var year : String = ""

    var label : String = ""

    @Column(columnDefinition = "TEXT")
    var description : String = ""
}