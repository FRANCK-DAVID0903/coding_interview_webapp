package com.b2i.tontine.domain.account.port

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.utils.OperationResult

/**
 * Manage User registration
 *
 * @author Alexwilfriedo
 **/
interface IRegisterUser {

    fun saveUser(model: User): OperationResult<User>
}