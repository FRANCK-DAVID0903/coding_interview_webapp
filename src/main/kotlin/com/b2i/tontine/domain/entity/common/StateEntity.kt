package com.b2i.tontine.domain.entity.common

import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class StateEntity : AuditableEntity<String>() {

    var active: Boolean = false
}