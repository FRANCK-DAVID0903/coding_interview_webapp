package com.dave.coding_interview_webapp.domain.serviceProvider.entity

import com.dave.coding_interview_webapp.domain.account.entity.User
import com.dave.coding_interview_webapp.domain.account.entity.UserType
import com.dave.coding_interview_webapp.domain.activity_sector.entity.ActivitySector
import com.dave.coding_interview_webapp.domain.experience.entity.Experience
import com.dave.coding_interview_webapp.domain.formation.entity.Formation
import com.dave.coding_interview_webapp.domain.reference.entity.Reference
import javax.persistence.*

@Entity
@DiscriminatorValue(UserType.SERVICE_PROVIDER)
class ServiceProvider: User() {

    @Column(columnDefinition = "TEXT")
    var bio : String = ""

    @ManyToOne
    var activitySector : ActivitySector? = null

    var cniRecto : String = ""

    var cniVerso : String = ""

    //0 = compte créé
    //1 = compte validé
    //2 = compte approuvé
    //3 = compte desactivé
    var status : Int = 0

    @OneToMany(cascade = [CascadeType.ALL])
    var formations : MutableSet<Formation> = mutableSetOf()

    @OneToMany(cascade = [CascadeType.ALL])
    var references : MutableSet<Reference> = mutableSetOf()

    @OneToMany(cascade = [CascadeType.ALL])
    var experiences : MutableSet<Experience> = mutableSetOf()

}