package com.b2i.tontine.domain.penalty.entity

import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.association_member.entity.AssociationMember
import com.b2i.tontine.domain.entity.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:37
 */
@Entity
class Penalty : BaseEntity() {

    var label: String = ""

    var amount: Double = 0.0

    // Association

    @ManyToOne(targetEntity = Association::class, optional = false)
    var association : Association? = null

    /*@ManyToOne(targetEntity = AssociationMember::class, optional = false)
    var associationMember : AssociationMember? = null*/

}