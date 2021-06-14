package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.account.entity.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository : JpaRepository<Admin,Long> {
}