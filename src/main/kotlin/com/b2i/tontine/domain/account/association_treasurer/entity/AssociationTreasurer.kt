package com.b2i.tontine.domain.account.association_treasurer.entity

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.account.entity.UserType
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:14
 */
@Entity
@DiscriminatorValue(UserType.ASSOCIATION_TREASURER)
class AssociationTreasurer() : User() {
}