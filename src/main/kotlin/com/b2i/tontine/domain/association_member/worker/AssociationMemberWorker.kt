package com.b2i.tontine.domain.association_member.worker

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.association_member.entity.AssociationMember
import com.b2i.tontine.domain.association_member.port.AssociationMemberDomain
import com.b2i.tontine.infrastructure.db.repository.AssociationMemberRepository
import com.b2i.tontine.infrastructure.db.repository.AssociationRepository
import com.b2i.tontine.infrastructure.db.repository.UserRepository
import com.b2i.tontine.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:18
 */
@Service
class AssociationMemberWorker : AssociationMemberDomain {

    @Autowired
    lateinit var associationMemberRepository: AssociationMemberRepository

    @Autowired
    lateinit var associationRepository: AssociationRepository

    @Autowired
    lateinit var userRepository: UserRepository

    override fun addMemberToAssociation(association_id: Long, member_id: Long): OperationResult<AssociationMember> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: AssociationMember? = null

        val optionalMember = userRepository.findById(member_id)
        val optionalAssociation = associationRepository.findById(association_id)

        if (!optionalMember.isPresent) {
            errors["not_found"] = "user_not_found"
        }
        if (!optionalAssociation.isPresent) {
            errors["not_found"] = "association_not_found"
        }

        if (errors.isEmpty()) {
            val isMemberInAssociation =
                associationMemberRepository.findByAssociationAndUser(optionalAssociation.get(), optionalMember.get())

            if (isMemberInAssociation.isPresent) {
                errors["error"] = "association_member_exist"
            } else {
                val associationMember = AssociationMember(optionalMember.get(), optionalAssociation.get())
                associationMember.association = optionalAssociation.get()
                associationMember.user = optionalMember.get()

                data = associationMemberRepository.save(associationMember)
            }
        }

        return OperationResult(data, errors)
    }

    override fun findAllMembersInAssociation(association: Association): List<AssociationMember> =
        associationMemberRepository.findAllByAssociation(association)

    override fun countAssociationMembers(association: Association): Long {
        return associationMemberRepository.countAllByAssociation(association)
    }

    override fun findAllByUser(member: User): MutableList<AssociationMember> {
        return associationMemberRepository.findAllByUser(member)
    }

    override fun countAllByUser(member: User): Long {
        return associationMemberRepository.countAllByUser(member)
    }

    override fun findByAssociationAndUser(association: Association, member: User): Optional<AssociationMember> {
        return associationMemberRepository.findByAssociationAndUser(association,member)
    }

    override fun updateRoleMember(associationMember: AssociationMember,role:String): OperationResult<AssociationMember> {

        val errors: MutableMap<String, String> = mutableMapOf()
        var data: AssociationMember? = null

        val optionalMember = userRepository.findById(associationMember.user!!.id)
        val optionalAssociation = associationRepository.findById(associationMember.association!!.id)

        if (!optionalMember.isPresent) {
            errors["not_found"] = "user_not_found"
        }
        if (!optionalAssociation.isPresent) {
            errors["not_found"] = "association_not_found"
        }

        if (errors.isEmpty()) {
            val isMemberInAssociation =
                    associationMemberRepository.findByAssociationAndUser(optionalAssociation.get(), optionalMember.get())

            if (isMemberInAssociation.isPresent) {
                val associationMember = AssociationMember(optionalMember.get(), optionalAssociation.get())
                associationMember.association = optionalAssociation.get()
                associationMember.user = optionalMember.get()
                associationMember.role = role

                if (role != "MEMBER"){
                    val allReadyBe = associationMemberRepository.findByRoleAndAssociation(role,optionalAssociation.get())
                    if (allReadyBe.isPresent){
                        errors["error"] = "association_member_does_exist"
                    }
                    else{
                        data = associationMemberRepository.save(associationMember)
                    }
                }

            } else {

                errors["error"] = "association_member_does_exist"
            }
        }

        return OperationResult(data, errors)
    }

    override fun findByAssociationAndRole(association: Association, role: String): Optional<AssociationMember> {
        return associationMemberRepository.findByAssociationAndRole(association,role)
    }
}