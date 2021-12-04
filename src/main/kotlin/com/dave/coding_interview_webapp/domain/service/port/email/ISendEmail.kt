package com.dave.coding_interview_webapp.domain.service.port.email

import com.dave.coding_interview_webapp.domain.account.entity.User

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