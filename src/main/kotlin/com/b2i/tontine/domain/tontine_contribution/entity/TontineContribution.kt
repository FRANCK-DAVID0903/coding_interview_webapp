package com.b2i.tontine.domain.tontine_contribution.entity

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.entity.common.BaseEntity
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.b2i.tontine.domain.tontine.entity.TontineType
import com.b2i.tontine.domain.tontine_periodicity.entity.TontinePeriodicity
import com.fasterxml.jackson.annotation.JsonBackReference
import java.util.*
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/26
 * @Time: 11:54
 */
@Entity
class TontineContribution : BaseEntity() {

    var contributed : Boolean = false

    var contributionAmount : Double = 0.0

    var contributionDate : Date? = null

    var paymentMethod: String = ""

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member : Member? = null

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tontine_id")
    var tontine : Tontine? = null

    @ManyToOne(targetEntity = TontinePeriodicity::class, optional = false)
    var tontinePeriodicity: TontinePeriodicity? = null

    //0 = Cotisation faite par le presi ou l'admin association
    //1 = Cotisation faite par le membre en attente de validation
    var state : Int = 0

}