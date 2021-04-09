package com.b2i.tontine.domain.account.port

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.utils.OperationResult
import java.util.*

/**
 * Handle User authentication
 *
 * @author Alexwilfriedo
 **/
interface IAuthenticateUser {

    fun findByUsername(username: String): Optional<User>

    fun findByEmail(email: String): Optional<User>

    fun findUserById(id: Long): Optional<User>

    fun authenticateUser(username: String, password: String): OperationResult<User>

}