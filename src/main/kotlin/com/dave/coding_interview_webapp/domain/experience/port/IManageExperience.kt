package com.dave.coding_interview_webapp.domain.experience.port

import com.dave.coding_interview_webapp.domain.experience.entity.Experience
import java.util.*

interface IManageExperience {

    fun findById(id:Long): Optional<Experience>

    fun deleteById(experience: Experience): Unit
}