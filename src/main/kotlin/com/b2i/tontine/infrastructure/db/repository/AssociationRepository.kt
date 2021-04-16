package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.association.entity.Association
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/14
 * @Time: 14:26
 */
interface AssociationRepository : JpaRepository<Association, Long> {

    fun findByName(name: String) : Optional<Association>

    fun findByEmail(email: String) : Optional<Association>

    fun findByPhoneNumber(phoneNumber: String) : Optional<Association>

}