package com.dave.coding_interview_webapp.domain.account.port

import com.dave.coding_interview_webapp.domain.account.entity.Admin
import com.dave.coding_interview_webapp.domain.account.entity.User
import com.dave.coding_interview_webapp.utils.OperationResult
import java.util.*

/**
 * Handle User authentication
 **/
interface IAuthenticateUser {

    fun findByUsername(username: String): Optional<User>

    fun findByEmail(email: String): Optional<User>

    fun findUserById(id: Long): Optional<User>

    fun authenticateUser(username: String, password: String): OperationResult<User>

    fun findTypeBy(id: Long): String

    fun isTakenUserByEmail(email: String):Boolean

    fun isTakenUserByUsername(username:String):Boolean

    fun findAllAdmin():MutableList<Admin>

}