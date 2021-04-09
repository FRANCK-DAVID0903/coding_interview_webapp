package com.b2i.tontine.domain.account.port

import com.b2i.tontine.domain.entity.common.Role
import java.util.*

/**
 * @author Alexwilfriedo
 **/
interface IManageRole {

    fun save(model: Role): Role

    fun count(): Long

    fun findByName(name: String): Optional<Role>

    fun findAll(): List<Role>

    fun findById(id: Long): Optional<Role>
}