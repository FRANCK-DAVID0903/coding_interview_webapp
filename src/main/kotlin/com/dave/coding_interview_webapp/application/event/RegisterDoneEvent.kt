package com.dave.coding_interview_webapp.application.event

import com.dave.coding_interview_webapp.domain.account.entity.User
import org.springframework.context.ApplicationEvent

class RegisterDoneEvent (val user: User):ApplicationEvent(user)