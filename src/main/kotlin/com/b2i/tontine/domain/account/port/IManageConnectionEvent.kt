package com.b2i.tontine.domain.account.port

import com.b2i.tontine.domain.entity.common.ConnectionEvent
import com.b2i.tontine.utils.OperationResult

/**
 * @author Alexwilfriedo
 **/
interface IManageConnectionEvent {

    fun save(model: ConnectionEvent): OperationResult<ConnectionEvent>

    fun isFirstConnection(id: Long): Boolean
}