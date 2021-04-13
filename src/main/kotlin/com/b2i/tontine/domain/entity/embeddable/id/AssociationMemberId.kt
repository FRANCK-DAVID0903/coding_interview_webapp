package com.b2i.tontine.domain.entity.embeddable.id

import javax.persistence.Embeddable
import java.io.Serializable

/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 16:37
 */
@Embeddable
data class AssociationMemberId(private val associationId : Long, private val userId : Long) : Serializable