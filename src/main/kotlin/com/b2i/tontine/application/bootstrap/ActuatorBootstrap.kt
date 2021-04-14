package com.b2i.tontine.application.bootstrap

import com.b2i.tontine.domain.account.entity.Actuator
import com.b2i.tontine.domain.account.entity.UserType
import com.b2i.tontine.domain.account.port.RoleDomain
import com.b2i.tontine.domain.account.port.UserDomain
import java.util.*

object ActuatorBootstrap {

    fun seed(roleDomain: RoleDomain, userDomain: UserDomain) {
        if (userDomain.count() == 0L) {
            roleDomain.findByName(UserType.ACTUATOR).ifPresent { role ->

                val actuator = Actuator(
                        username = "actuator",
                        password = "open",
                        roles = Collections.singleton(role)
                )
                actuator.firstname = "Actuator"
                actuator.lastname = "B21"
                actuator.contact.email="actuator@gmail.com"
                actuator.contact.phone="+225 0700000000"
                userDomain.saveUser(actuator)
            }
        }
    }
}