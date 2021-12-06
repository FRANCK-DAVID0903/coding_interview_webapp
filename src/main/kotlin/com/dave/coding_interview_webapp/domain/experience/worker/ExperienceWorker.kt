package com.dave.coding_interview_webapp.domain.experience.worker

import com.dave.coding_interview_webapp.domain.experience.entity.Experience
import com.dave.coding_interview_webapp.domain.experience.port.ExperienceDomain
import com.dave.coding_interview_webapp.infrastructure.db.repository.ExperienceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class ExperienceWorker:ExperienceDomain {

    @Autowired
    lateinit var experienceRepository: ExperienceRepository

    override fun findById(id: Long): Optional<Experience> {
        return experienceRepository.findById(id)
    }

    override fun deleteById(experience: Experience) {
        return experienceRepository.delete(experience)
    }
}