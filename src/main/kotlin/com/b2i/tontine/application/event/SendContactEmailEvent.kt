package com.b2i.tontine.application.event

import com.b2i.tontine.domain.account.entity.UserNonRegistered
import org.apache.catalina.User
import org.springframework.context.ApplicationEvent

class SendContactEmailEvent(val user:UserNonRegistered):ApplicationEvent(user) {
}