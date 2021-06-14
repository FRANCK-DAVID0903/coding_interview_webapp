package com.b2i.tontine.domain.notification.worker

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.notification.entity.Notification
import com.b2i.tontine.domain.notification.port.NotificationDomain
import com.b2i.tontine.infrastructure.db.repository.NotificationRepository
import com.b2i.tontine.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class NotificationWorker:NotificationDomain {

    @Autowired
    lateinit var notificationRepository: NotificationRepository

    override fun countAllByUserAndState(user: User, state: Int): Long {
        return notificationRepository.countAllByUserAndState(user,state)
    }

    override fun findAllByUserAndState(user: User, state: Int): MutableList<Notification> {
        return notificationRepository.findAllByUserAndState(user,state)
    }

    override fun saveNotification(notification: Notification): OperationResult<Notification> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: Notification? = null

        notificationRepository.save(notification)
        return OperationResult(data, errors)
    }

    override fun readNotification(id: Long): OperationResult<Notification> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: Notification? = null

        var notif = notificationRepository.findById(id).orElse(null)

        if (notif == null){
            errors["not_found"] = "not_found"
        }
        else{
            notif.state = 1
            data = notificationRepository.save(notif)
        }


        return OperationResult(data, errors)
    }

    override fun findNotifById(id: Long): Optional<Notification> {
        return notificationRepository.findById(id)
    }
}