package com.dave.coding_interview_webapp.application.listener

import com.dave.coding_interview_webapp.application.event.SendContactEmailEvent
import com.dave.coding_interview_webapp.domain.service.helper.EmailHelper
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class SendContactEmailListener(private val emailHelper: EmailHelper) : ApplicationListener<SendContactEmailEvent> {

    override fun onApplicationEvent(event: SendContactEmailEvent) {
        if(event != null) {
            val user = event.user
            var message = user.msg
            if(message!!.isEmpty()) {
                message = "Le contenu du message est vide."
            }
            if (!user.email.equals("") && !user.email.equals("null")){
                try {
                    emailHelper.sendMsg(user.fullname!!,"Demande d'information",user.email!!, message)
                } catch (e:Exception) {
                    print(e.message!!)
                }
            }
        }
    }

}