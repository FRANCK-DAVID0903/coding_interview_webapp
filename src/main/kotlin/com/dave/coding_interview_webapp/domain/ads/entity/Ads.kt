package com.dave.coding_interview_webapp.domain.ads.entity

import com.dave.coding_interview_webapp.domain.activity_sector.entity.ActivitySector
import com.dave.coding_interview_webapp.domain.client.entity.Client
import com.dave.coding_interview_webapp.domain.entity.common.BaseEntity
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Ads: BaseEntity() {

    @ManyToOne
    var client : Client? = null

    var title : String = ""

    var date : Date? = null

    @ManyToOne
    var activitySector : ActivitySector? = null

    @Column(columnDefinition = "TEXT")
    var description : String = ""

    // 0 = desactivé
    // 1 = activé
    // 2 = supprimé
    var status : Int = 0

}