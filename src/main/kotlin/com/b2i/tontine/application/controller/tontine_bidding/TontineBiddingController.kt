package com.b2i.tontine.application.controller.tontine_bidding

import com.b2i.tontine.application.controlForm.Color
import com.b2i.tontine.application.controlForm.ControlForm
import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.port.UserDomain
import com.b2i.tontine.domain.tontine_bidding.port.TontineBiddingDomain
import com.b2i.tontine.domain.tontine_periodicity.port.TontinePeriodicityDomain
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/05/06
 * @Time: 19:58
 */
@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_ASSOCIATION])
class TontineBiddingController(
    private val tontineBiddingDomain: TontineBiddingDomain,
    private val tontinePeriodicityDomain: TontinePeriodicityDomain,
    private val authenticationFacade: AuthenticationFacade,
    private val userDomain: UserDomain,
    private val messageSource: MessageSource
) : BaseController("/backend/tontine/", ControllerEndpoint.BACKEND_ASSOCIATION) {

    @PostMapping(value = ["/{association_id}/tontines/{tontine_id}/{periodicity_id}/bidding"])
    fun memberMakingBidding(
        redirectAttributes: RedirectAttributes,
        @PathVariable association_id: String,
        @PathVariable tontine_id: String,
        @PathVariable periodicity_id: String,
        @RequestParam member_id: String,
        locale: Locale
    ): String {
        var url = "$association_id/tontines/$tontine_id"

        when {
            association_id.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("association_member_id_association_empty", null, locale),
                    Color.red
                )
            }
            tontine_id.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontine_id_empty", null, locale),
                    Color.red
                )
            }
            member_id.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("user_not_found", null, locale),
                    Color.red
                )
            }
        }

        return ""
    }


}