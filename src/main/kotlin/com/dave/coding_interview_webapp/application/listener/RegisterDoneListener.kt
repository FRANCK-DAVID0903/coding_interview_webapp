package com.dave.coding_interview_webapp.application.listener

import com.dave.coding_interview_webapp.application.event.RegisterDoneEvent
import com.dave.coding_interview_webapp.domain.service.helper.EmailHelper
import org.springframework.context.ApplicationListener

class RegisterDoneListener(private val emailHelper: EmailHelper):ApplicationListener<RegisterDoneEvent> {

    override fun onApplicationEvent(event: RegisterDoneEvent) {

        if(event!=null){
            if(!event.user.contact.email.equals("") && !event.user.contact.email.equals(null) ){
                emailHelper.sendEmail(event.user.contact.email,event.user.username,event.user.password)
            }
        }

    }


}