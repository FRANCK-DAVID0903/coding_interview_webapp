package com.b2i.tontine.application.controller.profil

import com.b2i.social.application.event.SendEmailEvent
import com.b2i.tontine.application.controlForm.Color
import com.b2i.tontine.application.controlForm.ControlForm
import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.entity.FolderSrc
import com.b2i.tontine.domain.account.entity.UserType
import com.b2i.tontine.domain.account.member.port.MemberDomain
import com.b2i.tontine.domain.account.port.UserDomain
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.contribution.port.ContributionDomain
import com.b2i.tontine.domain.tontine.port.TontineDomain
import com.b2i.tontine.infrastructure.local.storage.StorageService
import com.b2i.tontine.utils.OperationResult
import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.text.SimpleDateFormat
import java.util.*

@Controller
@RequestMapping(value =[ControllerEndpoint.BACKEND_PROFILE])
class ProfileController(
        private val userDomain: UserDomain,
        private val authenticationFacade: AuthenticationFacade,
        private val tontineDomain: TontineDomain,
        private val contributionDomain: ContributionDomain,
        private val memberDomain: MemberDomain,
        private val messageSource: MessageSource,
        private val storageService: StorageService
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
                return "myrofile"
            }

            UserType.BACKOFFICE_SUPER_ADMIN -> {
                return "myprofile"
            }

            else -> redirectTo("dashboard/dashboard_basic_user")
        }
    }

    @PostMapping("/updateInfos")
    fun updateAssociation(redirectAttributes: RedirectAttributes,
                          @RequestParam("firstname") firstname: String,
                          @RequestParam("lastname") lastname: String,
                          @RequestParam("dateNaissance") dateNaissance: String,
                          @RequestParam("photo") photo: MultipartFile,
                          locale: Locale) : String {

        var url = "myProfile"

        when {
            firstname.isEmpty() -> {
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        messageSource.getMessage("user_firstname_empty", null, locale),
                        Color.red
                )
            }
            lastname.isEmpty() -> {
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        messageSource.getMessage("user_lastname_empty", null, locale),
                        Color.red
                )
            }
            dateNaissance.isEmpty() -> {
                ControlForm.redirectAttribute(
                        redirectAttributes,
                        messageSource.getMessage("user_birthday_empty", null, locale),
                        Color.red
                )
            }
            else -> {
                memberDomain.findById(authenticationFacade.getAuthenticatedUser().get().id).ifPresent{ member ->

                    member.firstname = firstname
                    member.lastname = lastname
                    member.birthday = SimpleDateFormat("YYYY-MM-dd").parse(dateNaissance)

                    if (photo.originalFilename != ""){
                        if(!storageService.storeMix(photo,"", FolderSrc.SRC_MEMBER).first){
                            member.photo = photo.originalFilename.toString().toLowerCase(Locale.FRENCH)
                        }else{
                            ControlForm.redirectAttribute(
                                    redirectAttributes,
                                    messageSource.getMessage("user_avatar_file_not_good", null, locale),
                                    Color.red
                            )
                        }
                    }

                    //On va update les info du membre
                    val result = memberDomain.updateInfosMember(member)

                    if (
                            ControlForm.verifyHashMapRedirect(
                                    redirectAttributes,
                                    result.errors!!,
                                    messageSource.getMessage("member_save_success", null, locale)
                            )
                    ) {
                        url = "myProfile"
                    }


                }
            }
        }

        return redirectTo(url)
    }

}