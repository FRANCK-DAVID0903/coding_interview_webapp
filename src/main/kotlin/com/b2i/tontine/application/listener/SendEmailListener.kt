package com.b2i.social.application.listener

import com.b2i.social.application.event.SendEmailEvent
import com.b2i.social.domain.service.helper.EmailHelper
import com.b2i.tontine.domain.account.entity.User
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class SendEmailListener(private val emailHelper: EmailHelper):ApplicationListener<SendEmailEvent> {
    override fun onApplicationEvent(event: SendEmailEvent) {
        if (event!=null){
            val user : User = event.user

            if (!user.contact.email.equals("") && !user.contact.email.equals("null")){

                emailHelper.sendEmail(user.contact.email,user.username,user.password)
            }
        }
    }


}