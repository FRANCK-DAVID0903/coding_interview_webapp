package com.dave.coding_interview_webapp.domain.entity.common

import com.dave.coding_interview_webapp.domain.entity.common.AuditableEntity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

/**
 * @author alexwilfriedo
 */
@MappedSuperclass
abstract class TableGenerationBaseEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.TABLE)
        var id: Long? = null
) : AuditableEntity<String>()
