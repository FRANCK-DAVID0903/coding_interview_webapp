package com.dave.coding_interview_webapp.domain.reference.worker

import com.dave.coding_interview_webapp.domain.reference.entity.Reference
import com.dave.coding_interview_webapp.domain.reference.port.ReferenceDomain
import com.dave.coding_interview_webapp.infrastructure.db.repository.ReferenceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class ReferenceWorker: ReferenceDomain {

    @Autowired
    lateinit var referenceRepository: ReferenceRepository

    override fun findById(id: Long): Optional<Reference> {
        return referenceRepository.findById(id)
    }

    override fun deleteById(reference: Reference) {
        return referenceRepository.delete(reference)
    }
}