package com.dave.coding_interview_webapp.domain.reference.entity

import com.dave.coding_interview_webapp.domain.entity.common.BaseEntity
import com.dave.coding_interview_webapp.domain.serviceProvider.entity.ServiceProvider
import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Reference: BaseEntity() {

    var year : String = ""

    var label : String = ""

    @Column(columnDefinition = "TEXT")
    var description : String = ""

    @JsonBackReference
    @ManyToOne
    var serviceProvider: ServiceProvider?=null
}