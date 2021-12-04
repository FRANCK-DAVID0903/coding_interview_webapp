package com.dave.coding_interview_webapp.infrastructure.db.repository

import com.dave.coding_interview_webapp.domain.account.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(username: String): Optional<User>

    fun findByContactEmail(contact_email: String): Optional<User>

    fun countAllByUsername(username: String): Long

    fun countAllByContactEmail(email: String):Long

    @Query(value = "SELECT U.user_type FROM user_account U WHERE U.id = ?1", nativeQuery = true)
    fun findTypeBy(id: Long): String

}