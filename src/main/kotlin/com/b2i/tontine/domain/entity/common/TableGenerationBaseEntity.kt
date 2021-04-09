package com.b2i.tontine.domain.entity.common

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
