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

    override fun createTontine(association: Association): OperationResult<Tontine> {
        TODO("Not yet implemented")
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