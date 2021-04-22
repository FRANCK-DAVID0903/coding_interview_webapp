package com.b2i.tontine.domain.association_member.entity

import com.b2i.tontine.domain.account.entity.AssociationRole
import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.account.entity.UserType
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.entity.common.AuditableEntity
import com.b2i.tontine.domain.entity.embeddable.id.AssociationMemberId
import javax.persistence.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:07
 */
@Entity
class AssociationMember() : AuditableEntity<String>() {

    @EmbeddedId
    private var associationMemberId : AssociationMemberId? = null

    var role : String = AssociationRole.MEMBER

    @ManyToOne(targetEntity = Association::class, optional = false)
    @MapsId("associationId")
    var association : Association? = null

    @ManyToOne(targetEntity = User::class, optional = false)
    @MapsId("userId")
    var member : User? = null

    var state:Int=0

    constructor(member : User, association : Association) : this() {
        this.associationMemberId = AssociationMemberId(association.id, member.id)
        this.association = association
        this.member = member
    }

}