package com.dave.coding_interview_webapp.domain.entity.common

import com.dave.coding_interview_webapp.domain.entity.common.BaseEntity
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class OwnedEntity : BaseEntity() {

    var owner: Long = -1L
}