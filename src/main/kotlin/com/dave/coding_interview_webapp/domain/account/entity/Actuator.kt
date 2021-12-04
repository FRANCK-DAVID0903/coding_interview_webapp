package com.dave.coding_interview_webapp.domain.account.entity

import com.dave.coding_interview_webapp.domain.entity.common.Role
import com.dave.coding_interview_webapp.domain.account.entity.User
import com.dave.coding_interview_webapp.domain.account.entity.UserType
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