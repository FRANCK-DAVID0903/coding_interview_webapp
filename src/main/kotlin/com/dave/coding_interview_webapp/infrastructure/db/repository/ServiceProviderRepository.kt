package com.dave.coding_interview_webapp.infrastructure.db.repository

import com.dave.coding_interview_webapp.domain.serviceProvider.entity.ServiceProvider
import org.springframework.data.jpa.repository.JpaRepository

interface ServiceProviderRepository: JpaRepository<ServiceProvider,Long> {

    fun findAllByStatus(status:Int): MutableList<ServiceProvider>
}