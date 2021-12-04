package com.dave.coding_interview_webapp.domain.entity.common

import com.dave.coding_interview_webapp.domain.entity.common.BaseEntity
import javax.persistence.MappedSuperclass

/**
 * @author alexwilfriedo
 */
@MappedSuperclass
abstract class GeolocatedBaseEntity(var longitude: Double = 0.toDouble(),
                                    var latitude: Double = 0.toDouble()) : BaseEntity()
