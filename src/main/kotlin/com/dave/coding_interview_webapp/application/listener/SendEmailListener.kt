package com.dave.coding_interview_webapp.application.listener

import com.dave.coding_interview_webapp.application.event.SendEmailEvent
import com.dave.coding_interview_webapp.domain.account.entity.User
import com.dave.coding_interview_webapp.domain.service.helper.EmailHelper
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class SendEmailListener(private val emailHelper: EmailHelper):ApplicationListener<SendEmailEvent> {
    override fun onApplicationEvent(event: SendEmailEvent) {
        if (event!=null){
            val user : User = event.user

            if (!user.contact.email.equals("") && !user.contact.email.equals("null")){

                try {
                    emailHelper.sendHtmlEmail(user.contact.email, user)
                } catch (e:Exception) {
                    print(e.message!!)
                }
            }
        }
    }


}