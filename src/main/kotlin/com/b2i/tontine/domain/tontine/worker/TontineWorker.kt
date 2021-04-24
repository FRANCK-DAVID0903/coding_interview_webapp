package com.b2i.tontine.domain.tontine.worker

import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine.port.TontineDomain
import com.b2i.tontine.infrastructure.db.repository.AssociationRepository
import com.b2i.tontine.infrastructure.db.repository.TontineRepository
import com.b2i.tontine.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:41
 */
@Service
class TontineWorker : TontineDomain {

    @Autowired
    lateinit var tontineRepository: TontineRepository

    @Autowired
    lateinit var associationRepository: AssociationRepository

    override fun createTontine(tontine: Tontine, association_id: Long): OperationResult<Tontine> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: Tontine? = null

        val optionalAssociation = associationRepository.findById(association_id)
        if (!optionalAssociation.isPresent) {
            errors["not_found"] = "association_not_found"
        }

        if (tontine.name.isEmpty()) {
            errors["name"] = "tontine_name_empty"
        }

        if (tontine.numberOfParticipant <= 0) {
            errors["numberOfParticipant"] = "tontine_participant_null"
        }

        if (tontine.contributionAmount <= 0.0) {
            errors["numberOfParticipant"] = "tontine_contribution_amount_null"
        }

        if (errors.isEmpty()) {
            tontine.association = optionalAssociation.get()
            tontine.tontineGlobalAmount = tontine.numberOfParticipant * tontine.contributionAmount

            data = tontineRepository.save(tontine)
        }

        return OperationResult(data, errors)
    }

    override fun findTontineById(id: Long): Optional<Tontine> = tontineRepository.findById(id)

    override fun findTontineByName(name: String): Optional<Tontine> = tontineRepository.findByName(name)

    override fun findAllTontinesByAssociation(association: Association): List<Tontine> =
        tontineRepository.findAllByAssociation(association)

    override fun countAllTontinesByAssociation(association: Association): Long {
        return tontineRepository.countAllByAssociation(association)
    }

    override fun countAllTontines(): Long {
        return tontineRepository.count()
    }
}