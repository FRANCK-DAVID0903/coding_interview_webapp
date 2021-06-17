package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.association.entity.Association
import com.b2i.tontine.domain.contribution.entity.Contribution
import com.b2i.tontine.domain.region.entity.Region
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/14
 * @Time: 14:23
 */
interface ContributionRepository : JpaRepository<Contribution, Long> {

    fun findAllByUser(member: User): MutableList<Contribution>

    fun findAllByMonthNumber(month:Int): MutableList<Contribution>

    fun findAllByUserAndMonthNumber(member:User,month:Int): MutableList<Contribution>

    @Query("SELECT * FROM contribution cont, user_account ua, association ass, association_member am WHERE cont.member_id = am.user_id and ua.id = ass.point_id and ass.id = am.association_id and ua.region_id = ?1 and cont.month_number = ?2 and ass.id= ?3",nativeQuery = true)
    fun findAllCotisationByAssociationAndMonth(id:Long,month: Int,id_asso:Long):MutableList<Contribution>

    fun findAllByUserAndAssociation(user: User,association: Association):MutableList<Contribution>

    fun findAllByUserAndAssociationAndState(user: User,association: Association,state:Int): MutableList<Contribution>

    fun findAllByUserAndState(user: User,state: Int): MutableList<Contribution>


}