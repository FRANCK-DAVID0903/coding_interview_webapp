package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.account.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(username: String): Optional<User>

    @Query(value = "SELECT U.user_type FROM user_account U WHERE U.id = ?1", nativeQuery = true)
    fun findTypeBy(id: Long): String
}