package com.dave.coding_interview_webapp.domain.formation.worker

import com.dave.coding_interview_webapp.domain.formation.entity.Formation
import com.dave.coding_interview_webapp.domain.formation.port.FormationDomain
import com.dave.coding_interview_webapp.infrastructure.db.repository.FormationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class FormationWorker:FormationDomain {

    @Autowired
    lateinit var formationRepository: FormationRepository

    override fun findById(id: Long): Optional<Formation> {
        return formationRepository.findById(id)
    }

    override fun deleteById(formation: Formation) {
        return formationRepository.delete(formation)
    }
}