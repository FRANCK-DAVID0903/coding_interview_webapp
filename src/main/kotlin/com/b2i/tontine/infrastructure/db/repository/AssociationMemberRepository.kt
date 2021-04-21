package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.association_member.entity.AssociationMember
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/14
 * @Time: 14:25
 */
interface AssociationMemberRepository : JpaRepository<AssociationMember, Long> {

    fun findByAssociationAndMember(association: Association, member: User): Optional<AssociationMember>

    fun findAllByAssociation(association: Association): List<AssociationMember>

    fun countAllByAssociation(association: Association) : Long

}