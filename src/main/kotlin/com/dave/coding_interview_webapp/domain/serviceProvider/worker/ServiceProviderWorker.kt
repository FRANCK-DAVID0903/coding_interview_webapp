package com.dave.coding_interview_webapp.domain.serviceProvider.worker

import com.dave.coding_interview_webapp.domain.serviceProvider.entity.ServiceProvider
import com.dave.coding_interview_webapp.domain.serviceProvider.port.ServiceProviderDomain
import com.dave.coding_interview_webapp.infrastructure.db.repository.ServiceProviderRepository
import com.dave.coding_interview_webapp.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class ServiceProviderWorker:ServiceProviderDomain {

    @Autowired
    lateinit var serviceProviderRepository: ServiceProviderRepository
    override fun findAll(): MutableList<ServiceProvider> {
        return serviceProviderRepository.findAll()
    }

    override fun countAll(): Long {
        return serviceProviderRepository.count()
    }

    override fun saveProvider(serviceProvider: ServiceProvider): OperationResult<ServiceProvider> {
        var data : ServiceProvider? = null
        var errors : MutableMap<String,String> = mutableMapOf()

        if(errors.isEmpty()){
            data = serviceProviderRepository.save(serviceProvider)
        }

        return OperationResult(data, errors)
    }

    override fun findById(id: Long): Optional<ServiceProvider> {
        return serviceProviderRepository.findById(id)
    }

    override fun findAllByStatus(status: Int): MutableList<ServiceProvider> {
        return serviceProviderRepository.findAllByStatus(status)
    }
}