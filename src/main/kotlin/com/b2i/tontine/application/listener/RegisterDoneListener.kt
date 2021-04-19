package com.b2i.social.application.listener

import com.b2i.social.application.event.RegisterDoneEvent
import com.b2i.social.domain.service.helper.EmailHelper
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