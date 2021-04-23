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

    override fun addMemberToAssociation(association: Association, member: Member): OperationResult<AssociationMember> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: AssociationMember? = null

        val optionalMember = userRepository.findById(member.id)
        val optionalAssociation = associationRepository.findById(association.id)

        if (!optionalMember.isPresent) {
            errors["not_found"] = "user_not_found"
        }
        if (!optionalAssociation.isPresent) {
            errors["not_found"] = "association_not_found"
        }

        if (errors.isEmpty()) {
            val isMemberInAssociation = associationMemberRepository.findByAssociationAndMember(association, member)

            if (isMemberInAssociation.isPresent) {
                errors["error"] = "member_exist"
            } else {
                val associationMember = AssociationMember()
                associationMember.association = optionalAssociation.get()
                associationMember.member = optionalMember.get()

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

    override fun findAllByMember(member: User): MutableList<AssociationMember> {
        return associationMemberRepository.findAllByMember(member)
    }

    override fun countAllByMember(member: User): Long {
        return associationMemberRepository.countAllByMember(member)
    }
}