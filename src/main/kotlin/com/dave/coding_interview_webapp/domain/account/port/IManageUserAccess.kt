package com.dave.coding_interview_webapp.domain.account.port

import com.b2i.projectName.domain.account.port.IManageUserPassword
import com.dave.coding_interview_webapp.utils.OperationResult

/**
 * Handle User's access management
 *
 * @author Alexwilfriedo
 **/
interface IManageUserAccess : IManageUserPassword {

    fun lock(username: String): OperationResult<Boolean>

    fun unlock(username: String): OperationResult<Boolean>
}