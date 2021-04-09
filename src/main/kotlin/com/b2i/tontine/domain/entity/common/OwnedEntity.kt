package com.b2i.tontine.domain.entity.common

import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class OwnedEntity : BaseEntity() {

    var owner: Long = -1L
}