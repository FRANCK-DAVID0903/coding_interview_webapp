package com.dave.coding_interview_webapp.domain.formation.port

import com.dave.coding_interview_webapp.domain.formation.entity.Formation
import java.util.*

interface IManageFormation {
    fun findById(id:Long): Optional<Formation>

    fun deleteById(formation: Formation): Unit
}