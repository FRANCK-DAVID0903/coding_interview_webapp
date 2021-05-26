package com.b2i.tontine.domain.tontine_request.entity

import com.b2i.tontine.domain.account.member.entity.Member
import com.b2i.tontine.domain.entity.common.BaseEntity
import com.b2i.tontine.domain.tontine.entity.Tontine
import com.fasterxml.jackson.annotation.JsonBackReference
import java.util.*
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/14
 * @Time: 15:44
 */
@Entity
class TontineRequest() : BaseEntity() {

    var approved : Boolean = false

    var requestDate : Date? = null

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var beneficiary : Member? = null

    var hasTaken : Boolean = false

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tontine_id")
    var tontine : Tontine? = null

    var state : Int = 0

}