package com.b2i.social.application.listener

import com.b2i.social.application.event.AssociationMemberEvent
import com.b2i.tontine.domain.association.port.AssociationDomain
import org.springframework.context.ApplicationListener

class AssociationMemberListener(private val associationDomain: AssociationDomain):ApplicationListener<AssociationMemberEvent> {

    override fun onApplicationEvent(event: AssociationMemberEvent) {
        if(event!=null){

        }
    }

}