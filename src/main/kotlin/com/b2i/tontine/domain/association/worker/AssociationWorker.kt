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

        if (association.email.isEmpty()) {
            errors["email"] = "association_email_empty"
        }

        if (association.phoneNumber.isEmpty()) {
            errors["phoneNumber"] = "association_phoneNumber_empty"
        }

        if (errors.isEmpty()) {

            if (association.id == -1L) {
                data = associationRepository.save(association)
            } else {
                val optionalAssociation = associationRepository.findById(association.id)

                if (optionalAssociation.isPresent) {
                    val associationToUpdate = optionalAssociation.get()
                    associationToUpdate.name = association.name
                    associationToUpdate.acronym = association.acronym
                    associationToUpdate.email = association.email
                    associationToUpdate.phoneNumber = association.phoneNumber
                    associationToUpdate.description = association.description

                    data = associationRepository.save(associationToUpdate)
                } else {
                    errors["not_found"] = "association_not_found"
                }

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