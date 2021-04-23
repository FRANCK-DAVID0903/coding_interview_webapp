package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.tontine.entity.Tontine
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/14
 * @Time: 14:15
 */
interface TontineRepository : JpaRepository<Tontine, Long> {

    fun findByName(name: String): Optional<Tontine>

    fun findAllByAssociation(association: Association): List<Tontine>

    fun countAllByAssociation(association: Association): Long

}