package com.b2i.tontine.application.controller

import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.entity.UserType
import com.b2i.tontine.domain.account.member.port.MemberDomain
import com.b2i.tontine.domain.account.port.RoleDomain
import com.b2i.tontine.domain.account.port.UserDomain
import com.b2i.tontine.infrastructure.local.storage.StorageService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/12
 * @Time: 15:22
 */
@Controller
class MainController(
        private val authenticationFacade: AuthenticationFacade,
        private val userDomain: UserDomain,
        private val memberDomain: MemberDomain,
        private val roleDomain: RoleDomain,
        private val eventPublisher: ApplicationEventPublisher,
        private val storageService: StorageService
): BaseController(ControllerEndpoint.BACKEND_DASHBOARD) {

    @GetMapping(value = ["/backend", "/backend"])
    fun dashboardPage(
            model:Model,
            redirectAttributes: RedirectAttributes
    ): String {

        val user = authenticationFacade.getAuthenticatedUser().get()
        val userRoleId=authenticationFacade.getAuthenticatedUser().get().roleId()

        val userType = userDomain.findTypeBy(user.id)

        return when (userType)
        {

            UserType.ACTUATOR -> {
                forwardTo("dashboard/dashboard_actuator")
            }

            UserType.BACKOFFICE_ADMIN -> {

                redirectTo("dashboard/dashboard_admin")
            }

            UserType.BACKOFFICE_BASIC_USER -> {
                forwardTo("dashboard/dashboard_basic_user")
            }

            UserType.BACKOFFICE_SUPER_ADMIN -> {
                return "account/home"
            }

            else -> redirectTo("dashboard/dashboard_basic_user")
        }
    }

}