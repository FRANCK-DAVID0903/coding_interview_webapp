package com.b2i.projectName.domain.account.port

import com.dave.coding_interview_webapp.domain.account.entity.User
import com.dave.coding_interview_webapp.utils.OperationResult

/**
 * Handle User's access management
 *
 * @author Alexwilfriedo
 **/
interface IManageUserPassword {

    fun forgotPassword(user: User): OperationResult<Boolean>

    fun updatePassword(user: User, newPassword: String): OperationResult<Boolean>
}