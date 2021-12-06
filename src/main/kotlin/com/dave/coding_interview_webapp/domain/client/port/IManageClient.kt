package com.dave.coding_interview_webapp.domain.client.port

import com.dave.coding_interview_webapp.domain.client.entity.Client
import com.dave.coding_interview_webapp.utils.OperationResult
import java.util.*

interface IManageClient {

    fun authenticateClient(username: String, password:String): OperationResult<Client>

    fun findById(id:Long): Optional<Client>

    fun findAll(): MutableList<Client>

    fun findAllByStatus(status:Long): MutableList<Client>

    fun saveClient(client:Client): OperationResult<Client>

}