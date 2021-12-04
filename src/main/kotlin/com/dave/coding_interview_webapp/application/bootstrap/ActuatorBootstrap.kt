package com.dave.coding_interview_webapp.application.bootstrap

import com.dave.coding_interview_webapp.domain.account.entity.Actuator
import com.dave.coding_interview_webapp.domain.account.entity.UserType
import com.dave.coding_interview_webapp.domain.account.port.RoleDomain
import com.dave.coding_interview_webapp.domain.account.port.UserDomain
import java.util.*

object ActuatorBootstrap {

    fun seed(roleDomain: RoleDomain, userDomain: UserDomain) {
        if (userDomain.count() == 0L) {
            roleDomain.findByName(UserType.ACTUATOR).ifPresent { role ->

                val actuator = Actuator(
                        username = "dave",
                        password = "dave123",
                        roles = Collections.singleton(role)
                )
                actuator.firstname = "Dave"
                actuator.lastname = "dave"
                actuator.contact.email="actuator@gmail.com"
                actuator.contact.phone="+225 0700000000"
                userDomain.saveUser(actuator)
            }
        }
    }
}