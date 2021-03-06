package com.dave.coding_interview_webapp.domain.account.worker

import com.dave.coding_interview_webapp.domain.account.entity.Admin
import com.dave.coding_interview_webapp.domain.account.entity.User
import com.dave.coding_interview_webapp.domain.account.port.UserDomain
import com.dave.coding_interview_webapp.infrastructure.db.repository.AdminRepository
import com.dave.coding_interview_webapp.infrastructure.db.repository.UserRepository
import com.dave.coding_interview_webapp.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserWorker : UserDomain {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var adminRepository: AdminRepository

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

    override fun isTakenUserByEmail(email: String): Boolean {
        return userRepository.countAllByContactEmail(email) >0L
    }

    override fun isTakenUserByUsername(username: String): Boolean {
        return userRepository.countAllByUsername(username) >0L
    }

    override fun findAllAdmin(): MutableList<Admin> {
        return adminRepository.findAll()
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
            errors["email"] = "Le num??ro est requis pour la cr??ation d'un utilisateur"
        }

        if (model.firstname.isNullOrEmpty()) {
            errors["firstname"] = "Le pr??nom est requis pour la cr??ation d'un utilisateur"
        }

        if (model.lastname.isNullOrEmpty()) {
            errors["lastname"] = "Le nom est requis pour la cr??ation d'un utilisateur"
        }

        if (errors.isEmpty()) {
            model.enabled = true
            data = userRepository.save(model)
        }

        return OperationResult(data, errors)
    }

    override fun findByUsername(username: String): Optional<User> = userRepository.findByUsername(username)

    override fun findByEmail(email: String): Optional<User> = userRepository.findByContactEmail(email)

    override fun findUserById(id: Long): Optional<User> = userRepository.findById(id)

    override fun count(): Long = userRepository.count()

}