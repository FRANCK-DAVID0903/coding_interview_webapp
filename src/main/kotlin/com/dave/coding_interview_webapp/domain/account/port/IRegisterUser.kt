package com.dave.coding_interview_webapp.domain.account.port

import com.dave.coding_interview_webapp.domain.account.entity.User
import com.dave.coding_interview_webapp.utils.OperationResult

/**
 * Manage User registration
 *
 **/
interface IRegisterUser {

    fun saveUser(model: User): OperationResult<User>
}