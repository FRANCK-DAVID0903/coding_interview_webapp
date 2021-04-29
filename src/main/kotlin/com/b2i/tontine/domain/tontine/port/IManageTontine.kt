package com.b2i.tontine.domain.tontine.port

import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.utils.OperationResult
import java.util.*

interface IManageTontine {

    fun createTontine(tontine: Tontine, association_id: Long): OperationResult<Tontine>

    fun extendTontineMembershipDeadline(id: Long, membershipDeadline: String): OperationResult<Tontine>

    fun closeTontineMembership(id: Long): OperationResult<Tontine>

    fun findTontineById(id: Long): Optional<Tontine>

    fun findTontineByName(name: String): Optional<Tontine>

    fun findAllTontinesByAssociation(association: Association): List<Tontine>

    fun countAllTontinesByAssociation(association: Association): Long

    fun countAllTontines(): Long

}