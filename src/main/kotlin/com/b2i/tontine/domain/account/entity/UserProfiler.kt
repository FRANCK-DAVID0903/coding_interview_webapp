package com.b2i.tontine.domain.account.entity

/**
 * @author Alexwilfriedo
 */

object UserProfiler {

    fun profile(user: User) = UserProfile(user)

    class UserProfile(user: User) {

        val id = user.id

        val username = user.username

        val firstname = user.firstname

        val lastname = user.lastname

        val fullname = "$firstname $lastname"

        val isEnabled = user.isEnabled

        var actuator: Boolean = false

        var superAdmin: Boolean = false

        var admin: Boolean = false

        var basicUser: Boolean = false

        init {
            val roles = user.roles
            actuator = roles!!.any { it.name == UserType.ACTUATOR }
            superAdmin = roles.any { it.name == UserType.BACKOFFICE_SUPER_ADMIN }
            admin = roles.any { it.name == UserType.BACKOFFICE_ADMIN }
            basicUser = roles.any { it.name == UserType.BACKOFFICE_BASIC_USER }
        }
    }
}