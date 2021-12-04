package com.dave.coding_interview_webapp.domain.report.entity

import com.dave.coding_interview_webapp.domain.ads.entity.Ads
import com.dave.coding_interview_webapp.domain.client.entity.Client
import com.dave.coding_interview_webapp.domain.entity.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Report: BaseEntity() {

    @ManyToOne
    var ads: Ads? = null

    @ManyToOne
    var client : Client? = null

    var comment :String = ""
}