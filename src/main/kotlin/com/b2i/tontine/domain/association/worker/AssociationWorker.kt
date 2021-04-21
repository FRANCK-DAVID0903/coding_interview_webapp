package com.b2i.tontine.domain.association.worker

import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.association.port.AssociationDomain
import com.b2i.tontine.infrastructure.db.repository.AssociationRepository
import com.b2i.tontine.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:23
 */
@Service
class AssociationWorker : AssociationDomain {

    @Autowired
    lateinit var messageSource : MessageSource

    @Autowired
    lateinit var associationRepository: AssociationRepository

    override fun saveAssociation(association: Association): OperationResult<Association> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: Association? = null

        if (association.name.isEmpty()) {
            errors["name"] = "association_name_empty"
        }

        if (association.acronym.isEmpty()) {
            errors["acronym"] = "association_acronym_empty"
        }

        if (errors.isEmpty()) {
            val optionalAssociationEmail = associationRepository.findByEmail(association.email)
            val optionalAssociationPhoneNumber = associationRepository.findByPhoneNumber(association.phoneNumber)

            if (association.id == -1L) {
                when {
                    association.email.isEmpty() -> {
                        errors["email"] = "association_email_empty"
                    }
                    association.phoneNumber.isEmpty() -> {
                        errors["phoneNumber"] = "association_phoneNumber_empty"
                    }
                    optionalAssociationEmail.isPresent -> {
                        errors["error"] = "association_email_already_exist"
                    }
                    optionalAssociationPhoneNumber.isPresent -> {
                        errors["error"] = "association_phoneNumber_already_exist"
                    }
                    else -> {
                        data = associationRepository.save(association)
                    }
                }
            } else {
                val optionalAssociation = associationRepository.findById(association.id)

                if (optionalAssociation.isPresent) {
                    val associationToUpdate = optionalAssociation.get()
                    associationToUpdate.name = association.name
                    associationToUpdate.acronym = association.acronym
                    associationToUpdate.description = association.description

                    data = associationRepository.save(associationToUpdate)
                } else {
                    errors["not_found"] = "association_not_found"
                }

            }

        }

        return  OperationResult(data, errors)
    }

    override fun updateAssociationEmail(id: Long, email: String): OperationResult<Association> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: Association? = null

        if (email.isEmpty()) {
            errors["email"] = "association_email_empty"
        }

        if (errors.isEmpty()) {
            val optionalAssociation = associationRepository.findById(id)

            if (!optionalAssociation.isPresent) {
                errors["not_found"] = "association_not_found"
            } else {
                val association = optionalAssociation.get()
                val optionalAssociationEmail = associationRepository.findByEmail(email)

                if (optionalAssociationEmail.isPresent) {
                    val associationEmail = optionalAssociationEmail.get()
                    if (associationEmail.id == association.id) {
                        association.email = email
                    } else {
                        errors["error"] = "association_email_already_exist"
                    }
                } else {
                    association.email = email
                }

                data = associationRepository.save(association)
            }
        }

        return  OperationResult(data, errors)
    }

    override fun updateAssociationPhoneNumber(id: Long, phoneNumber: String): OperationResult<Association> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: Association? = null

        if (phoneNumber.isEmpty()) {
            errors["phoneNumber"] = "association_phoneNumber_empty"
        }

        if (errors.isEmpty()) {
            val optionalAssociation = associationRepository.findById(id)

            if (!optionalAssociation.isPresent) {
                errors["not_found"] = "association_not_found"
            } else {
                val association = optionalAssociation.get()
                val optionalAssociationPhoneNumber = associationRepository.findByPhoneNumber(phoneNumber)

                if (optionalAssociationPhoneNumber.isPresent) {
                    val associationPhone = optionalAssociationPhoneNumber.get()
                    if (associationPhone.id == association.id) {
                        association.phoneNumber = phoneNumber
                    } else {
                        errors["error"] = "association_phoneNumber_already_exist"
                    }
                } else {
                    association.phoneNumber = phoneNumber
                }

                data = associationRepository.save(association)
            }
        }

        return  OperationResult(data, errors)
    }

    override fun changeAssociationState(id: Long): OperationResult<Boolean> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data = false

        val optionalAssociation = associationRepository.findById(id)

        if (!optionalAssociation.isPresent) {
            errors["not_found"] = "association_not_found"
        } else {
            val association = optionalAssociation.get()

            if (association.state == 0) {
                association.state = 1
            } else {
                association.state = 0
            }

            associationRepository.save(association)
            data = true
        }

        return  OperationResult(data, errors)
    }

    override fun findAssociationById(id: Long): Optional<Association> = associationRepository.findById(id)

    override fun findAssociationByName(name: String): Optional<Association> = associationRepository.findByName(name)

    override fun findAllAssociations(): List<Association> = associationRepository.findAll()

    override fun countAllAssociations(): Long {
        return associationRepository.count()
    }
}