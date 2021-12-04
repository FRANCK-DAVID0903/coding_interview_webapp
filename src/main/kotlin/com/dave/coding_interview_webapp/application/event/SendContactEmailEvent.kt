package com.dave.coding_interview_webapp.application.event

import com.dave.coding_interview_webapp.domain.account.entity.UserNonRegistered
import org.springframework.context.ApplicationEvent

class SendContactEmailEvent(val user: UserNonRegistered) : ApplicationEvent(user)