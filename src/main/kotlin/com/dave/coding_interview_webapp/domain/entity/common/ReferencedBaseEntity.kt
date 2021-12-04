package com.dave.coding_interview_webapp.domain.entity.common

import com.dave.coding_interview_webapp.domain.entity.common.BaseEntity
import javax.persistence.MappedSuperclass

/**
 * @author alexwilfriedo
 */
@MappedSuperclass
abstract class ReferencedBaseEntity : BaseEntity() {

    var reference: String = ""
}
