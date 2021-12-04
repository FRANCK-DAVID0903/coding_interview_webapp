package com.dave.coding_interview_webapp.application.bootstrap

import com.dave.coding_interview_webapp.domain.account.entity.UserType
import com.dave.coding_interview_webapp.domain.account.port.RoleDomain
import com.dave.coding_interview_webapp.domain.entity.common.Role

object RoleBootstrap {

    fun seed(domain: RoleDomain) {

        if (domain.count() == 0L) {
            domain.save(Role(UserType.ACTUATOR, UserType.ACTUATOR))
            domain.save(Role(UserType.BACKOFFICE_SUPER_ADMIN, UserType.BACKOFFICE_SUPER_ADMIN))
            domain.save(Role(UserType.BACKOFFICE_ADMIN, UserType.BACKOFFICE_ADMIN))
            domain.save(Role(UserType.SERVICE_PROVIDER, UserType.SERVICE_PROVIDER))
            domain.save(Role(UserType.CLIENT, UserType.CLIENT))
        }
    }
}