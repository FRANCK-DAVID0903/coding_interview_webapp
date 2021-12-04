package com.dave.coding_interview_webapp.domain.activity_sector.entity

import com.dave.coding_interview_webapp.domain.entity.common.BaseEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity

@Entity
class ActivitySector(): BaseEntity() {

    @JsonIgnore
    var label : String = ""

    @JsonIgnore
    var description: String = ""
}