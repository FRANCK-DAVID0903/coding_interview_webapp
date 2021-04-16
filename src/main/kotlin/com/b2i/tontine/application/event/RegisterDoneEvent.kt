package com.b2i.social.application.event

import com.b2i.tontine.domain.account.entity.User
import org.springframework.context.ApplicationEvent

class RegisterDoneEvent (val user: User):ApplicationEvent(user) {
}