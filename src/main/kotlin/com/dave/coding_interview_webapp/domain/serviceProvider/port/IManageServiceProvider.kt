package com.dave.coding_interview_webapp.domain.serviceProvider.port

import com.dave.coding_interview_webapp.domain.serviceProvider.entity.ServiceProvider
import com.dave.coding_interview_webapp.utils.OperationResult
import java.util.*

interface IManageServiceProvider {
    fun findAll(): MutableList<ServiceProvider>

    fun countAll(): Long

    fun saveProvider(serviceProvider: ServiceProvider): OperationResult<ServiceProvider>

    fun findById(id:Long) : Optional<ServiceProvider>

    fun findAllByStatus(status:Int): MutableList<ServiceProvider>
}