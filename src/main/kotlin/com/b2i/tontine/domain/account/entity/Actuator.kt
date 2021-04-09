package com.b2i.tontine.domain.account.entity

import com.b2i.tontine.domain.entity.common.Role
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue(UserType.ACTUATOR)
class Actuator() : User()
{
    constructor( username: String, password : String, roles: Set<Role> = setOf() ) : this() {
        this.username = username
        this.password = password
        this.roles = roles
    }

}