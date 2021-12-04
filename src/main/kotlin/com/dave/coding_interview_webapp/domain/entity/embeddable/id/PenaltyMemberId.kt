package com.b2i.projectName.domain.entity.embeddable.id

import java.io.Serializable
import javax.persistence.Embeddable


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 16:57
 */
@Embeddable
data class PenaltyMemberId(private val penaltyId : Long, private val userId: Long) : Serializable
