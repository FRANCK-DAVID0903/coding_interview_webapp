package com.b2i.tontine.domain.notification.port

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.notification.entity.Notification
import com.b2i.tontine.utils.OperationResult
import java.util.*

interface IManageNotification {

    fun countAllByUserAndState(user: User, state:Int): Long

    fun findAllByUserAndState(user: User, state:Int): MutableList<Notification>

    fun saveNotification(notification: Notification):OperationResult<Notification>

    fun readNotification(id:Long):OperationResult<Notification>

    fun findNotifById(id:Long):Optional<Notification>
}