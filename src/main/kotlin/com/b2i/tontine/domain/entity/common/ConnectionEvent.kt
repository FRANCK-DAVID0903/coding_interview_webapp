package com.b2i.tontine.domain.entity.common

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.entity.embeddable.Device
import javax.persistence.*

/**
 * @author alexwilfriedo
 */
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
