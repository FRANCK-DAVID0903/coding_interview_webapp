package com.b2i.tontine.domain.account.worker

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.account.port.UserDomain
import com.b2i.tontine.infrastructure.db.repository.UserRepository
import com.b2i.tontine.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author Alexwilfriedo
 **/
@Service
class UserWorker : UserDomain {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun authenticateUser(username: String, password: String): OperationResult<User> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: User? = null

        if (username.isEmpty() || password.isEmpty()) {
            errors["credentials"] = "Le nom d'utilisateur et le mot de passe sont obligatoires"
        }

        if (errors.isEmpty()) {
            val optionalUser = userRepository.findByUsername(username)
            val badCredentialsError = "Le nom d'utilisateur ou le mot de passe est incorrect"

            if (!optionalUser.isPresent) {
                errors["credentials"] = badCredentialsError
            } else {
                val user = optionalUser.get()
                if (!BCryptPasswordEncoder().matches(password, user.password)) {
                    errors["credentials"] = badCredentialsError
                } else {
                    data = user
                }
            }
        }

        return OperationResult(data, errors)
    }

    override fun findTypeBy(id: Long): String {
        return userRepository.findTypeBy(id)
    }

    override fun lock(username: String): OperationResult<Boolean> {
        TODO("Not yet implemented")
    }

    override fun unlock(username: String): OperationResult<Boolean> {
        TODO("Not yet implemented")
    }

    override fun forgotPassword(user: User): OperationResult<Boolean> {
        TODO("Not yet implemented")
    }

    override fun updatePassword(user: User, newPassword: String): OperationResult<Boolean> {
        TODO("Not yet implemented")
    }

    override fun saveUser(model: User): OperationResult<User> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: User? = null

        if (model.contact.email.isEmpty()) {
            errors["email"] = "Le numéro est requis pour la création d'un utilisateur"
        }

        if (model.firstname.isNullOrEmpty()) {
            errors["firstname"] = "Le prénom est requis pour la création d'un utilisateur"
        }

        if (model.lastname.isNullOrEmpty()) {
            errors["lastname"] = "Le nom est requis pour la création d'un utilisateur"
        }

        if (errors.isEmpty()) {
            model.enabled = true
            data = userRepository.save(model)
        }

        return OperationResult(data, errors)
    }

    override fun findByUsername(username: String): Optional<User> = userRepository.findByUsername(username)

    override fun findByEmail(email: String): Optional<User> {
        TODO("Not yet implemented")
    }

    override fun findUserById(id: Long): Optional<User> = userRepository.findById(id)

    override fun count(): Long = userRepository.count()

}