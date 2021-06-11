package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.notification.entity.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository:JpaRepository<Notification,Long> {

    fun countAllByUserAndState(user:User,state:Int): Long

    fun findAllByUserAndState(user:User,state:Int): MutableList<Notification>

}