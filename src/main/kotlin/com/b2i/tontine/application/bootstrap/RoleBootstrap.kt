package com.b2i.tontine.application.bootstrap

import com.b2i.tontine.domain.account.entity.UserType
import com.b2i.tontine.domain.account.port.RoleDomain
import com.b2i.tontine.domain.entity.common.Role

object RoleBootstrap {

    fun seed(domain: RoleDomain) {

        if (domain.count() == 0L) {
            domain.save(Role(UserType.ACTUATOR, UserType.ACTUATOR))
            domain.save(Role(UserType.BACKOFFICE_SUPER_ADMIN, UserType.BACKOFFICE_SUPER_ADMIN))
            domain.save(Role(UserType.BACKOFFICE_BASIC_USER, UserType.BACKOFFICE_BASIC_USER))
            domain.save(Role(UserType.BACKOFFICE_ADMIN, UserType.BACKOFFICE_ADMIN))
            domain.save(Role(UserType.ASSOCIATION_MEMBER, UserType.ASSOCIATION_MEMBER))
            domain.save(Role(UserType.ASSOCIATION_ADMIN, UserType.ASSOCIATION_ADMIN))
        }
    }
}