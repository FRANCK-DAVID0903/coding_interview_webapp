package com.dave.coding_interview_webapp.domain.reference.port

import com.dave.coding_interview_webapp.domain.reference.entity.Reference
import java.util.*

interface IManageReference {

    fun findById(id:Long): Optional<Reference>

    fun deleteById(reference: Reference)
}