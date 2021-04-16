package com.b2i.tontine.domain.association.port

import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.utils.OperationResult
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/15
 * @Time: 11:50
 */
interface IManageAssociation {

    fun saveAssociation(association: Association) : OperationResult<Association>

    fun changeAssociationState(id: Long): OperationResult<Boolean>

    fun findAssociationById(id: Long): Optional<Association>

    fun findAssociationByName(name: String): Optional<Association>

    fun findAllAssociations(): List<Association>

    fun countAllAssociations(): Long

}