package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.association_member.entity.AssociationMember
import org.springframework.data.jpa.repository.JpaRepository


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/14
 * @Time: 14:25
 */
interface AssociationMemberRepository : JpaRepository<AssociationMember, Long> {
}