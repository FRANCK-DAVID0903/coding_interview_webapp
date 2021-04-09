package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.account.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(username: String): Optional<User>
}