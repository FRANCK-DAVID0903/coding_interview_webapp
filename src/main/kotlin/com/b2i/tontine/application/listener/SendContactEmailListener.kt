package com.b2i.tontine.application.listener

import com.b2i.social.domain.service.helper.EmailHelper
import com.b2i.tontine.application.event.SendContactEmailEvent
import org.springframework.context.ApplicationListener

class SendContactEmailListener(private val emailHelper: EmailHelper):ApplicationListener<SendContactEmailEvent> {

    override fun onApplicationEvent(event: SendContactEmailEvent) {
        if(event != null) {
            val user = event.user
            var message = user.msg
            if(message.isEmpty()) {
                message = "Le contenu du message est vide."
            }
            if (!user.email.equals("") && !user.email.equals("null")){
                emailHelper.sendMsg(user.fullname,"Demande d'information",user.email,message)
            }
        }
    }

}