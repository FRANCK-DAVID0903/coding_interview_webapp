package com.b2i.tontine.domain.association_member.port

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.association_member.entity.AssociationMember
import com.b2i.tontine.utils.OperationResult


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/21
 * @Time: 21:41
 */
interface IManageAssociationMember {

    fun addMemberToAssociation(association: Association, member: Member): OperationResult<AssociationMember>

    fun findAllMembersInAssociation(association: Association): List<AssociationMember>

    fun countAssociationMembers(association: Association): Long

}