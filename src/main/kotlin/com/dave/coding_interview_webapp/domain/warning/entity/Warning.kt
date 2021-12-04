package com.dave.coding_interview_webapp.domain.warning.entity

import com.dave.coding_interview_webapp.domain.ads.entity.Ads
import com.dave.coding_interview_webapp.domain.entity.common.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Warning: BaseEntity(){

    @ManyToOne
    var ads : Ads? = null

    @Column(columnDefinition = "TEXT")
    var comment : String = ""


}