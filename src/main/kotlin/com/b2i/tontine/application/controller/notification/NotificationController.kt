package com.b2i.tontine.application.controller.notification

import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.domain.association.port.AssociationDomain
import com.b2i.tontine.domain.notification.port.NotificationDomain
import com.b2i.tontine.domain.tontine.port.TontineDomain
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_DASHBOARD])
class NotificationController(
        private val notificationDomain: NotificationDomain,
        private val tontineDomain: TontineDomain,
        private val associationDomain: AssociationDomain,
): BaseController(ControllerEndpoint.BACKEND_DASHBOARD) {

    @RequestMapping(value = ["/readNotification/{id_notif}"])
    fun readNotification(
            model: Model,
            @PathVariable id_notif: String,
    ): String {

        var url = "/backend"

        //get line notification line
        val notif = notificationDomain.findNotifById(id_notif.toLong()).orElse(null)

        if (notif == null){

        }else{
            //On dit qu'on a lu la notification
            notif.state = 1
            notificationDomain.saveNotification(notif)

            var id_asso = notif.association?.id
            var id_tontine = notif.tontine?.id
            url = "association/$id_asso/tontines/$id_tontine"
        }

        return redirectTo(url)
        //return forwardTo("/{association_id}/tontines/{tontine_id}")
    }
}