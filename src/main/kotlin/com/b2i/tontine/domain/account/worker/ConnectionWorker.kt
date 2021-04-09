package com.b2i.tontine.domain.account.worker

import com.b2i.tontine.domain.account.port.ConnectionDomain
import com.b2i.tontine.domain.entity.common.ConnectionEvent
import com.b2i.tontine.utils.OperationResult
import com.b2i.tontine.infrastructure.db.repository.ConnectionEventRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Alexwilfriedo
 **/
@Service
class ConnectionWorker : ConnectionDomain {

    @Autowired
    private lateinit var connectionEventRepository: ConnectionEventRepository

    override fun save(model: ConnectionEvent): OperationResult<ConnectionEvent> {
        return OperationResult(connectionEventRepository.save(model))
    }

    override fun isFirstConnection(id: Long): Boolean = connectionEventRepository.countAllByUser_Id(id) == 0L

}