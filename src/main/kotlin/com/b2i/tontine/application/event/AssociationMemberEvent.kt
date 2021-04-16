package com.b2i.social.application.event

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.association.entity.Association
import org.springframework.context.ApplicationEvent

class AssociationMemberEvent(private val member: Member, private val association: Association):
        ApplicationEvent(member) {
}