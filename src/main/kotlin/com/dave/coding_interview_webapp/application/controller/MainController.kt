package com.dave.coding_interview_webapp.application.controller

import com.dave.coding_interview_webapp.domain.account.entity.UserType
import com.dave.coding_interview_webapp.application.facade.AuthenticationFacade
import com.dave.coding_interview_webapp.domain.account.port.RoleDomain
import com.dave.coding_interview_webapp.domain.account.port.UserDomain
import com.dave.coding_interview_webapp.domain.activity_sector.port.ActivitySectorDomain
import com.dave.coding_interview_webapp.domain.ads.port.AdsDomain
import com.dave.coding_interview_webapp.domain.serviceProvider.port.ServiceProviderDomain
import com.dave.coding_interview_webapp.infrastructure.local.storage.StorageService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@Controller
class MainController(
    private val authenticationFacade: AuthenticationFacade,
    private val userDomain: UserDomain,
    private val roleDomain: RoleDomain,
    private val serviceProviderDomain: ServiceProviderDomain,
    private val activitySectorDomain: ActivitySectorDomain,
    private val adsDomain: AdsDomain,
    private val eventPublisher: ApplicationEventPublisher,
    private val storageService: StorageService
): BaseController(ControllerEndpoint.BACKEND_DASHBOARD) {

    @GetMapping(value = ["/", "/"])
    fun homePage(
        model:Model,
        redirectAttributes: RedirectAttributes
    ): String {

        model.addAttribute("allSectors", activitySectorDomain.findAllActivitySector())
        model.addAttribute("allAds", adsDomain.findAllByStatus(1))
        model.addAttribute("allProvider", serviceProviderDomain.findAllByStatus(2))

        return "frontend/home"
    }


    @GetMapping(value = ["/backend/", "/backend/"])
    fun dashboardPage(
            model:Model,
            redirectAttributes: RedirectAttributes
    ): String {

        val user = authenticationFacade.getAuthenticatedUser().get()
        val userRoleId=authenticationFacade.getAuthenticatedUser().get().roleId()

        model.addAttribute("userData",user)

        return when (userDomain.findTypeBy(user.id))
        {

            UserType.ACTUATOR -> {
                forwardTo("dashboard/dashboard_actuator")
            }

            UserType.CLIENT -> {

                forwardTo("dashboard/dashboard_client")
            }

            UserType.SERVICE_PROVIDER -> {

                forwardTo("dashboard/dashboard_service_provider")
            }

            else -> redirectTo("dashboard/dashboard_basic_user")
        }
    }



}