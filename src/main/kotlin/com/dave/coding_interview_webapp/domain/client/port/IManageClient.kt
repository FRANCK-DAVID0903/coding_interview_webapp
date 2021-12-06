package com.dave.coding_interview_webapp.domain.client.port

import com.dave.coding_interview_webapp.domain.client.entity.Client
import com.dave.coding_interview_webapp.utils.OperationResult

interface IManageClient {

    fun authenticateClient(username: String, password:String): OperationResult<Client>
}