package com.b2i.tontine.domain.association_member.port

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.association_member.entity.AssociationMember
import com.b2i.tontine.utils.OperationResult
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/21
 * @Time: 21:41
 */
interface IManageAssociationMember {

    fun addMemberToAssociation(association_id: Long, member_id: Long): OperationResult<AssociationMember>

    fun findAllMembersInAssociation(association: Association): List<AssociationMember>

    fun countAssociationMembers(association: Association): Long

    fun findAllByUser(member: User): MutableList<AssociationMember>

    fun countAllByUser(member: User): Long

    fun findByAssociationAndUser(association: Association, member: User): Optional<AssociationMember>

    fun updateRoleMember(associationMember: AssociationMember,role:String): OperationResult<AssociationMember>

    fun findByAssociationAndRole(association: Association,role: String):Optional<AssociationMember>

}