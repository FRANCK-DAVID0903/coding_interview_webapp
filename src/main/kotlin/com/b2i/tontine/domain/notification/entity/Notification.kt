package com.b2i.tontine.domain.notification.entity

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.entity.common.BaseEntity
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine_periodicity.entity.TontinePeriodicity
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.MapsId

@Entity
class Notification: BaseEntity() {

    @ManyToOne(targetEntity = User::class, optional = false)
    var user : User? = null

    @ManyToOne(targetEntity = Association::class, optional = false)
    var association : Association? = null

    @ManyToOne(targetEntity = Tontine::class, optional = false)
    var tontine : Tontine? = null

    @ManyToOne(targetEntity = TontinePeriodicity::class, optional = false)
    var periodicity : TontinePeriodicity? = null

    // 0 = non lu
    // 1 = lu
    var state: Int = 0
}