package com.b2i.tontine.application.controller.profil

import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.entity.UserType
import com.b2i.tontine.domain.account.port.UserDomain
import com.b2i.tontine.domain.contribution.port.ContributionDomain
import com.b2i.tontine.domain.tontine.port.TontineDomain
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping(value =[ControllerEndpoint.BACKEND_PROFILE])
class ProfileController(
        private val userDomain: UserDomain,
        private val authenticationFacade: AuthenticationFacade,
        private val tontineDomain: TontineDomain,
        private val contributionDomain: ContributionDomain
): BaseController(ControllerEndpoint.BACKEND_PROFILE) {

    @GetMapping(value = ["/myProfile", "/myProfile"])
    fun myProfilePage(
            model: Model,
            redirectAttributes: RedirectAttributes
    ): String {

        val user = authenticationFacade.getAuthenticatedUser().get()
        val userRoleId=authenticationFacade.getAuthenticatedUser().get().roleId()

        val userType = userDomain.findTypeBy(user.id)

        var associations = ""
        var tontines = ""
        var contributions = ""

        return when (userType)
        {

            UserType.ASSOCIATION_MEMBER -> {
                model.addAttribute("userData",user)
                model.addAttribute("allMyAssociations",associations)
                model.addAttribute("allMyTontines",tontines)
                model.addAttribute("allMyContributions",contributions)
                return "profile/myprofile"
            }

            UserType.BACKOFFICE_ADMIN -> {

                model.addAttribute("userInfos",user)
                return "myprofile"
            }

            UserType.BACKOFFICE_SUPER_ADMIN -> {
                return "myprofile"
            }

            else -> redirectTo("dashboard/dashboard_basic_user")
        }
    }

}