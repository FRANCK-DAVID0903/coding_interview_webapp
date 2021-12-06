package com.dave.coding_interview_webapp.domain.client.worker

import com.dave.coding_interview_webapp.domain.client.entity.Client
import com.dave.coding_interview_webapp.domain.client.port.ClientDomain
import com.dave.coding_interview_webapp.infrastructure.db.repository.ClientRepository
import com.dave.coding_interview_webapp.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Component
class ClientWorker:ClientDomain {
    @Autowired
    lateinit var clientRepository: ClientRepository

    override fun authenticateClient(username: String, password: String): OperationResult<Client> {

        val errors: MutableMap<String, String> = mutableMapOf()
        var data: Client? = null

        if (username.isEmpty()) {
            errors["username"] = "le username est requis"
        }

        if (password.isEmpty()) {
            errors["password"] = "le mot de passe est requis"
        }

        if (errors.isEmpty()) {
            val optionalClient = clientRepository.findByUsername(username)
            if (!optionalClient.isPresent) {
                errors["credentials"] = "Le username est incorrecte"
            } else {
                val client = optionalClient.get()
                if (client.locked) {
                    errors["locked"] = "le compte est inactif"
                } else {
                    if (!BCryptPasswordEncoder().matches(password, client.password)) {
                        errors["password"] = "password incorrect"
                    }
                    else{
                        data = client
                    }
                }
            }
        }

        return OperationResult(data, errors)

    }
}