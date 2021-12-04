package com.dave.coding_interview_webapp.domain.client.entity

import com.dave.coding_interview_webapp.domain.account.entity.User
import com.dave.coding_interview_webapp.domain.account.entity.UserType
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue(UserType.CLIENT)
class Client: User() {
    //0 = activé
    //1 = désactivé
    //2 = supprimé
    var status : Int = 0
}