package com.dave.coding_interview_webapp.domain.entity.common

import com.dave.coding_interview_webapp.domain.entity.common.AuditableEntity
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class StateEntity : AuditableEntity<String>() {

    var active: Boolean = false
}