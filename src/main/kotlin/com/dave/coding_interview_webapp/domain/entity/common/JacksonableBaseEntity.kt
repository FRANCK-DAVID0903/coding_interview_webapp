package com.b2i.projectName.domain.entity.common

import com.dave.coding_interview_webapp.domain.entity.common.BaseEntity
import javax.persistence.MappedSuperclass

/**
 * @author alexwilfriedo
 */

@MappedSuperclass
class JacksonableBaseEntity : BaseEntity()
