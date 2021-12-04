package com.dave.coding_interview_webapp.domain.account.port

import com.dave.coding_interview_webapp.domain.entity.common.ConnectionEvent
import com.dave.coding_interview_webapp.utils.OperationResult


interface IManageConnectionEvent {

    fun save(model: ConnectionEvent): OperationResult<ConnectionEvent>

    fun isFirstConnection(id: Long): Boolean
}