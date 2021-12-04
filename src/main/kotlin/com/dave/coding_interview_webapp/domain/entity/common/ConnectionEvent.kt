package com.dave.coding_interview_webapp.domain.entity.common

import com.dave.coding_interview_webapp.domain.account.entity.User
import com.dave.coding_interview_webapp.domain.entity.embeddable.Device
import javax.persistence.*

@Entity
data class ConnectionEvent(
    @field:Column(nullable = false, updatable = false) val username: String,
    @field:Column(name = "user_agent", nullable = false, updatable = false) val userAgent: String,
    @field:ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY, optional = false)
        @field:JoinColumn(name = "owner", referencedColumnName = "id")
        val user: User,
    @field:Embedded
        val device: Device
) : BaseEntity()
