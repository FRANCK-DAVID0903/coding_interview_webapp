package com.dave.coding_interview_webapp.infrastructure.db.repository


import com.dave.coding_interview_webapp.domain.entity.common.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoleRepository : JpaRepository<Role, Long> {

    fun findByName(name: String): Optional<Role>
}
