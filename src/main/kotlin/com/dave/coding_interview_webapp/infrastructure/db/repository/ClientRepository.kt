package com.dave.coding_interview_webapp.infrastructure.db.repository

import com.dave.coding_interview_webapp.domain.client.entity.Client
import org.springframework.data.jpa.repository.JpaRepository

interface ClientRepository: JpaRepository<Client,Long> {
}