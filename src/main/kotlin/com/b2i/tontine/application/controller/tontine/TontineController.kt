package com.b2i.tontine.application.controller.tontine

import com.b2i.tontine.application.controlForm.Color
import com.b2i.tontine.application.controlForm.ControlForm
import org.springframework.stereotype.Controller
import com.b2i.tontine.application.controller.BaseController
import com.b2i.tontine.application.controller.ControllerEndpoint
import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.entity.UserType
import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.account.member.port.MemberDomain
import com.b2i.tontine.domain.account.port.UserDomain
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.association.port.AssociationDomain
import com.b2i.tontine.domain.association_member.entity.AssociationMember
import com.b2i.tontine.domain.association_member.port.AssociationMemberDomain
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine.entity.TontineType
import com.b2i.tontine.domain.tontine.port.TontineDomain
import com.b2i.tontine.domain.tontine_contribution.entity.TontineContribution
import com.b2i.tontine.domain.tontine_contribution.port.TontineContributionDomain
import com.b2i.tontine.domain.tontine_periodicity.entity.TontinePeriodicity
import com.b2i.tontine.domain.tontine_periodicity.port.TontinePeriodicityDomain
import com.b2i.tontine.domain.tontine_request.entity.TontineRequest
import com.b2i.tontine.domain.tontine_request.port.TontineRequestDomain
import com.b2i.tontine.utils.OperationResult
import org.springframework.context.MessageSource
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/20
 * @Time: 22:03
 */
@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_ASSOCIATION])
class TontineController(
    private val tontineDomain: TontineDomain,
    private val associationDomain: AssociationDomain,
    private val associationMemberDomain: AssociationMemberDomain,
    private val tontineRequestDomain: TontineRequestDomain,
    private val tontinePeriodicityDomain: TontinePeriodicityDomain,
    private val authenticationFacade: AuthenticationFacade,
    private val userDomain: UserDomain,
    private val messageSource: MessageSource,
    private val memberDomain: MemberDomain,
    private val tontineContributionDomain: TontineContributionDomain
) :
    BaseController("/backend/tontine/", ControllerEndpoint.BACKEND_ASSOCIATION) {

    @GetMapping("/{association_id}/tontines/create")
    fun goToCreateTontine(model: Model, @PathVariable association_id: String): String {
        val association: Association? = associationDomain.findAssociationById(association_id.toLong()).orElse(null)

        if (association != null) {
            injectAssociation(model, association)
        }

        return forwardTo("add_tontine")
    }

    @PostMapping("/{association_id}/tontines/create")
    fun createTontine(
        redirectAttributes: RedirectAttributes,
        @PathVariable association_id: String,
        @RequestParam name: String,
        @RequestParam type: String,
        @RequestParam numberOfParticipant: String,
        @RequestParam contributionAmount: String,
        @RequestParam startDate: String,
        @RequestParam endDate: String,
        @RequestParam periodicity: String,
        @RequestParam membershipDeadline: String,
        locale: Locale
    ): String {
        var url = "$association_id/tontines/create"

        when {
            association_id.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("association_member_id_association_empty", null, locale),
                    Color.red
                )
            }
            numberOfParticipant.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontine_participant_empty", null, locale),
                    Color.red
                )
            }
            contributionAmount.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontine_contribution_amount_empty", null, locale),
                    Color.red
                )
            }
            startDate.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontine_contribution_startDate_empty", null, locale),
                    Color.red
                )
            }
            endDate.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontine_contribution_endDate_empty", null, locale),
                    Color.red
                )
            }
            else -> {
                val tontine = Tontine()
                tontine.name = name
                tontine.type = objectHelper.getTontineType(type)
                tontine.periodicity = objectHelper.getTontinePeriodicity(periodicity)
                tontine.numberOfParticipantEstimated = numberOfParticipant.toLong()
                tontine.contributionAmount = contributionAmount.toDouble()
                tontine.startDate = ControlForm.formatDate(startDate)
                tontine.endDate = ControlForm.formatDate(endDate)
                tontine.membershipDeadline = ControlForm.formatDate(membershipDeadline)

                val result: OperationResult<Tontine> = tontineDomain.createTontine(tontine, association_id.toLong())

                val err: MutableMap<String, String> = mutableMapOf()
                if (result.errors!!.isNotEmpty()) {
                    result.errors.forEach { (key, value) ->
                        err[key] = messageSource.getMessage(value, null, locale)
                    }
                }

                if (
                    ControlForm.verifyHashMapRedirect(
                        redirectAttributes,
                        err,
                        messageSource.getMessage("tontine_save_success", null, locale)
                    )
                ) {
                    url = "$association_id/tontines"
                }

            }
        }

        return redirectTo(url)
    }

    @PostMapping(value = ["/{association_id}/tontines/{tontine_id}/extendMembership"])
    fun extendTontineMembership(
        redirectAttributes: RedirectAttributes,
        @PathVariable association_id: String,
        @PathVariable tontine_id: String,
        @RequestParam membershipDeadline: String,
        locale: Locale
    ): String {
        var url = "$association_id/tontines"

        when {
            membershipDeadline.isEmpty() -> {
                ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontine_membership_deadline", null, locale),
                    Color.red
                )
            }
            else -> {
                val result: OperationResult<Tontine> =
                    tontineDomain.extendTontineMembershipDeadline(tontine_id.toLong(), membershipDeadline)

                val err: MutableMap<String, String> = mutableMapOf()
                if (result.errors!!.isNotEmpty()) {
                    result.errors.forEach { (key, value) ->
                        err[key] = messageSource.getMessage(value, null, locale)
                    }
                }

                if (
                    ControlForm.verifyHashMapRedirect(
                        redirectAttributes,
                        err,
                        messageSource.getMessage("tontine_save_success", null, locale)
                    )
                ) {
                    url = "$association_id/tontines/$tontine_id"
                }
            }
        }
        return redirectTo(url)
    }

    @PostMapping(value = ["/{association_id}/tontines/{tontine_id}/closeMembership"])
    fun closeTontineMembership(
        redirectAttributes: RedirectAttributes,
        @PathVariable association_id: String,
        @PathVariable tontine_id: String,
        locale: Locale
    ): String {
        var url = "$association_id/tontines"

        val result: OperationResult<Tontine> =
            tontineDomain.closeTontineMembership(tontine_id.toLong())

        val err: MutableMap<String, String> = mutableMapOf()
        if (result.errors!!.isNotEmpty()) {
            result.errors.forEach { (key, value) ->
                err[key] = messageSource.getMessage(value, null, locale)
            }
        }

        if (
            ControlForm.verifyHashMapRedirect(
                redirectAttributes,
                err,
                messageSource.getMessage("tontine_save_success", null, locale)
            )
        ) {
            url = "$association_id/tontines/$tontine_id"
        }

        return redirectTo(url)
    }

    @PostMapping(value = ["/{association_id}/tontines/{tontine_id}/generatePeriodicities"])
    fun tontineGeneratePeriodicities(
            redirectAttributes: RedirectAttributes,
            @PathVariable association_id: String,
            @PathVariable tontine_id: String,
            locale: Locale
    ): String {
        var url = "$association_id/tontines/$tontine_id"

        //get tontine by id
        val tontineSelected = tontineDomain.findTontineById(tontine_id.toLong())

        //find the number of tontine members request validate
        val numberPeriodities = tontineRequestDomain.countAllByTontineAndApproved(tontineSelected.get(),true)
        var decompte = 0L

        val nb = tontinePeriodicityDomain.countAllByTontine(tontineSelected.get())

        if (nb <= 0L)
        {
            while (decompte < numberPeriodities) {
                var period = TontinePeriodicity()

                decompte += 1
                period.periodicityNumber =decompte
                if (decompte == 1L){
                    period.nextPeriodicity = true
                }
                period!!.tontine =tontineSelected.get()
                //period!!.periodicityNumber =decompte
                val result: OperationResult<TontinePeriodicity> =
                        tontinePeriodicityDomain.saveTontinePeriodicity(period)

            }

            ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontinePeriodities_save_success", null, locale),
                    Color.green
            )
            url = "$association_id/tontines/$tontine_id"

        }
        else{

            ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontinePeriodities_allReady_generated", null, locale),
                    Color.red
            )
        }

        return redirectTo(url)
    }

    @PostMapping(value = ["/{association_id}/tontines/{tontine_id}/updatePeriodicity"])
    fun updatePeriodicity(
            redirectAttributes: RedirectAttributes,
            @PathVariable association_id: String,
            @PathVariable tontine_id: String,
            @RequestParam("id") id : String,
            @RequestParam("startDate") startDate : String,
            @RequestParam("endDate") endDate : String,
            locale: Locale
    ): String {
        var url = "$association_id/tontines/$tontine_id"

        if (startDate.isEmpty()){
                    ControlForm.redirectAttribute(
                            redirectAttributes,
                            messageSource.getMessage("association_member_id_association_empty", null, locale),
                            Color.red)
                }
        else if (endDate.isEmpty()){
            ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("association_member_id_association_empty", null, locale),
                    Color.red)
        }
        //get tontinePeriodicity by id
        var periodicity = tontinePeriodicityDomain.findById(id.toLong()).orElse(null)

        if (periodicity != null){
            periodicity.contributionStartDate = SimpleDateFormat("yyyy-MM-dd").parse(startDate)
            periodicity.contributionEndDate = SimpleDateFormat("yyyy-MM-dd").parse(endDate)
            periodicity.periodicityState = TontineType.OPENED

            //On genere les ligne de paiements des membres de la tontine pour la periodicité ouverte
            //On recupere la tontine
            val tontine = tontineDomain.findTontineById(tontine_id.toLong()).get()
            //Listes des membres de la tontine
            val listMember = tontineRequestDomain.findAllApprovedTontineMembers(tontine, true, 0)

            val allReadyContributions = tontineContributionDomain.findAllByTontinePeriodicity(periodicity).count()

            if (allReadyContributions <= 0){

                listMember.forEach{ member ->

                    val memberFinder = memberDomain.findById(member.beneficiary!!.id).get()
                    val contribution = TontineContribution()

                    contribution.member = memberFinder
                    contribution.tontine = tontine
                    contribution.tontinePeriodicity = periodicity

                    tontineContributionDomain.saveContribution(contribution)
                }
            }

            val result = tontinePeriodicityDomain.updateTontine(periodicity)

            val err: MutableMap<String, String> = mutableMapOf()
            if (result.errors!!.isNotEmpty()) {
                result.errors.forEach {
                    (key, value) -> err[key] = messageSource.getMessage(value, null, locale)
                }
            }

            if (
                    ControlForm.verifyHashMapRedirect(
                            redirectAttributes,
                            err,
                            messageSource.getMessage("periodicity_update_success", null, locale)
                    )
            ) {
                url = "$association_id/tontines/$tontine_id"
            }
        }


        return redirectTo(url)
    }

    @PostMapping(value = ["/{association_id}/tontines/{tontine_id}/closePeriodicity"])
    fun closePeriodicity(
            redirectAttributes: RedirectAttributes,
            @PathVariable association_id: String,
            @PathVariable tontine_id: String,
            @RequestParam("id")id: String,
            locale: Locale
    ): String {
        var url = "$association_id/tontines"

        //get periodicity by id

        var period = tontinePeriodicityDomain.findById(id.toLong()).orElse(null)

        if(period == null){
            ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("Periodicity_not_found", null, locale),
                    Color.green
            )
        }
        else{
            period.periodicityState = TontineType.CLOSED
            period.biddingState = TontineType.OPENED

            val result: OperationResult<TontinePeriodicity> = tontinePeriodicityDomain.saveTontinePeriodicity(period)

            ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontinePeriodities_save_success", null, locale),
                    Color.green
            )
            url = "$association_id/tontines/$tontine_id"
        }

        return redirectTo(url)
    }

    @PostMapping(value = ["/{association_id}/tontines/update/{tontine_id}"])
    fun updateTontine(
            redirectAttributes: RedirectAttributes,
            @PathVariable tontine_id: String,
            @PathVariable association_id: String,
            @RequestParam("type")type: String,
            @RequestParam("name")name: String,
            @RequestParam("numberOfParticipant")numberOfParticipant: String,
            @RequestParam("contributionAmount")contributionAmount: String,
            @RequestParam("periodicity")periodicity: String,
            locale: Locale
    ): String {


        var url = "$association_id/tontines/$tontine_id"
        var tontine = tontineDomain.findTontineById(tontine_id.toLong()).orElse(null)

        if (tontine == null){
            ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontine_not_found", null, locale),
                    Color.green
            )
        }
        else{

            //Update tonine now
            tontine.name = name
            tontine.numberOfParticipant = numberOfParticipant.toLong()
            tontine.contributionAmount = contributionAmount.toDouble()
            tontine.periodicity = periodicity
            tontine.type = type

            val result: OperationResult<Tontine> = tontineDomain.createTontine(tontine,association_id.toLong())

            ControlForm.redirectAttribute(
                    redirectAttributes,
                    messageSource.getMessage("tontine_update_success", null, locale),
                    Color.green
            )

        }

        return redirectTo(url)

    }


    @GetMapping("/{association_id}/tontines")
    fun listOfAssociationTontines(
        model: Model, @PathVariable association_id: String,
        locale: Locale
    ): String {

        when {
            association_id.isEmpty() -> {
                ControlForm.model(
                    model,
                    messageSource.getMessage("association_member_id_association_empty", null, locale),
                    Color.red
                )
            }
            else -> {
                val user = authenticationFacade.getAuthenticatedUser().get()
                when (userDomain.findTypeBy(user.id)) {
                    UserType.ACTUATOR -> {
                        val association = associationDomain.findAssociationById(association_id.toLong())
                        if (association.isPresent) {
                            val tontines = tontineDomain.findAllTontinesByAssociation(association.get())
                            model.addAttribute("association_tontines", tontines)

                        }
                        injectAssociation(model ,association.get())
                    }
                    UserType.ASSOCIATION_ADMIN -> {
                        val association = associationDomain.findAssociationById(association_id.toLong())
                        if (association.isPresent) {
                            val tontines = tontineDomain.findAllTontinesByAssociation(association.get())
                            model.addAttribute("association_tontines", tontines)
                        }
                        injectAssociation(model ,association.get())

                    }
                    UserType.ASSOCIATION_MEMBER -> {
                        val association = associationDomain.findAssociationById(association_id.toLong()).orElse(null)
                        if (association != null) {
                            val member = associationMemberDomain.findByAssociationAndUser(association, user)
                            if (member.isPresent) {
                                val connectedUser = member.get().role

                                if (connectedUser == "PDG") {
                                    val tontines = tontineDomain.findAllTontinesByAssociation(association)
                                    model.addAttribute("association_tontines", tontines)

                                } else {
                                    val tontines = tontineDomain.findAllTontinesByAssociationAndType(association, "OPENED")
                                    model.addAttribute("association_tontines", tontines)

                                }
                            }
                            injectAssociation(model ,association)
                        }
                    }

                }

            }
        }

        return forwardTo("list_tontine")
    }

    @GetMapping(value = ["/{association_id}/tontines/{tontine_id}"])
    fun tontineDetail(model: Model, @PathVariable tontine_id: String, @PathVariable association_id: String): String {
        var page = "detail_tontine"
        var isMembershipDeadline = false
        val association: Association? = associationDomain.findAssociationById(association_id.toLong()).orElse(null)
        val tontine: Tontine? = tontineDomain.findTontineById(tontine_id.toLong()).orElse(null)
        var dataMemberTontine = mutableMapOf<TontineRequest,Double>()

        if (association == null || tontine == null) {
            page = "list_tontine"
        } else {
            val associationMembers: List<AssociationMember> =
                associationMemberDomain.findAllMembersInAssociation(association)

            val today = ControlForm.formatDate(LocalDate.now().toString())
            if (tontine.membershipDeadline!!.before(today)) {
                isMembershipDeadline = true
            }
            val requestValidated = tontineRequestDomain.findAllApprovedTontineMembers(tontine, true, 0)

            requestValidated.forEach{ tonts ->
                val montant = tonts.beneficiary?.let { tontineContributionDomain.findAllByMemberAndContributed(it,true).sumByDouble { it!!.contributionAmount } }
                if (montant != null) {
                    dataMemberTontine.put(tonts,montant)
                }else{
                    dataMemberTontine.put(tonts,0.0)
                }
            }

            model.addAttribute("associationMembers", associationMembers)
            model.addAttribute("tontineRequests", tontineRequestDomain.findAllApprovedTontineMembers(tontine, false, 0))
            //model.addAttribute("tontineMembers", tontineRequestDomain.findAllApprovedTontineMembers(tontine, true, 0))
            model.addAttribute("tontineMembers", dataMemberTontine)
            model.addAttribute("tontinePeriodicities", tontinePeriodicityDomain.findAllByTontine(tontine).sortedBy { it.periodicityNumber })
            model.addAttribute("association", association)
            model.addAttribute("membershipDeadline", isMembershipDeadline)
            model.addAttribute("tontine", tontine)
            injectAssociation(model, association)

            }


        return forwardTo(page)
    }

    //Inject association in view
    fun injectAssociation(model: Model, association: Association) {
        model.addAttribute("association", association)
        val user = authenticationFacade.getAuthenticatedUser().get()
        var connectedUser = "actuator"

        model.addAttribute("connectedUserId", user.id)

        when (userDomain.findTypeBy(user.id)) {
            UserType.ASSOCIATION_MEMBER -> {
                val member = associationMemberDomain.findByAssociationAndUser(association, user)
                if (member.isPresent) {
                    connectedUser = member.get().role
                    model.addAttribute("connectedUser", connectedUser)
                }
            }
            UserType.ACTUATOR -> {
                model.addAttribute("connectedUser", connectedUser)
            }
            UserType.ASSOCIATION_ADMIN -> {
                connectedUser = "association_admin"
                model.addAttribute("connectedUser", connectedUser)
            }
        }
    }

}