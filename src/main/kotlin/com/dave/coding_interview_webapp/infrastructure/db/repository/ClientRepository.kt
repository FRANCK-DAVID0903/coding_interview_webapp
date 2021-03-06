package com.dave.coding_interview_webapp.infrastructure.db.repository

import com.dave.coding_interview_webapp.domain.client.entity.Client
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ClientRepository: JpaRepository<Client,Long> {

    fun findByUsername(username:String): Optional<Client>

    fun findAllByStatus(status:Long): MutableList<Client>
}