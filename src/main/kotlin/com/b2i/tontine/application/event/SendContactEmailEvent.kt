package com.b2i.tontine.application.event

import com.b2i.tontine.domain.account.entity.UserNonRegistered
import org.springframework.context.ApplicationEvent

class SendContactEmailEvent(val user:UserNonRegistered) : ApplicationEvent(user) {
}