package com.dave.coding_interview_webapp.domain.account.port

import com.dave.coding_interview_webapp.domain.entity.common.Role
import java.util.*


interface IManageRole {

    fun save(model: Role): Role

    fun count(): Long

    fun findByName(name: String): Optional<Role>

    fun findAll(): List<Role>

    fun findById(id: Long): Optional<Role>
}