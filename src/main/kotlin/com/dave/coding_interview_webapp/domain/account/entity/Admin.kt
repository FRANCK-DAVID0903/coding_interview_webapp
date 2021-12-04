package com.dave.coding_interview_webapp.domain.account.entity

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue(UserType.BACKOFFICE_ADMIN)
class Admin : User()