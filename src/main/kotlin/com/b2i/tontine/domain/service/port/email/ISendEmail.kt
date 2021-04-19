package com.b2i.tontine.domain.service.port.email

import com.b2i.tontine.domain.account.entity.User

/**
 * Handle email management
 *
 * @author Alexwilfriedo
 **/
interface ISendEmail {

    fun sendUserRegistrationEmail(user: User): Boolean

    fun sendUserForgotPasswordEmail(user: User): Boolean

    fun sendUserUpdatePasswordEmail(user: User): Boolean
}