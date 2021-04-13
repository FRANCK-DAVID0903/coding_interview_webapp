package com.b2i.tontine.domain.penalty_member.entity

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.entity.common.AuditableEntity
import com.b2i.tontine.domain.entity.embeddable.id.PenaltyMemberId
import com.b2i.tontine.domain.penalty.entity.Penalty
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.MapsId


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 16:57
 */
@Entity
class PenaltyMember() : AuditableEntity<String>(){

    @EmbeddedId
    private var penaltyMemberId : PenaltyMemberId? = null

    @ManyToOne(targetEntity = Penalty::class, optional = false)
    @MapsId("penaltyId")
    var penalty : Penalty? = null

    @ManyToOne(targetEntity = User::class, optional = false)
    @MapsId("userId")
    var user : User? = null

    var state : Int = 0

    constructor(member : User, penalty: Penalty) : this() {
        this.penaltyMemberId = PenaltyMemberId(penalty.id, member.id)
        this.penalty = penalty
        this.user = member
    }

}