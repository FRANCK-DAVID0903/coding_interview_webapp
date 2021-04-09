package com.b2i.tontine.domain.account.port

import com.b2i.tontine.utils.OperationResult

/**
 * Handle User's access management
 *
 * @author Alexwilfriedo
 **/
interface IManageUserAccess : IManageUserPassword {

    fun lock(username: String): OperationResult<Boolean>

    fun unlock(username: String): OperationResult<Boolean>
}