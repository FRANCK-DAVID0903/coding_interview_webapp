package com.dave.coding_interview_webapp.domain.account.worker

import com.dave.coding_interview_webapp.domain.account.port.ConnectionDomain
import com.dave.coding_interview_webapp.domain.entity.common.ConnectionEvent
import com.dave.coding_interview_webapp.utils.OperationResult
import com.dave.coding_interview_webapp.infrastructure.db.repository.ConnectionEventRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class ConnectionWorker : ConnectionDomain {

    @Autowired
    private lateinit var connectionEventRepository: ConnectionEventRepository

    override fun save(model: ConnectionEvent): OperationResult<ConnectionEvent> {
        return OperationResult(connectionEventRepository.save(model))
    }

    override fun isFirstConnection(id: Long): Boolean = connectionEventRepository.countAllByUser_Id(id) == 0L

}