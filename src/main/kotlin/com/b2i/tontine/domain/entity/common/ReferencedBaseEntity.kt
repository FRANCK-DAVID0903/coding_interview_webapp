package com.b2i.tontine.domain.entity.common

import javax.persistence.MappedSuperclass

/**
 * @author alexwilfriedo
 */
@MappedSuperclass
abstract class ReferencedBaseEntity : BaseEntity() {

    var reference: String = ""
}
