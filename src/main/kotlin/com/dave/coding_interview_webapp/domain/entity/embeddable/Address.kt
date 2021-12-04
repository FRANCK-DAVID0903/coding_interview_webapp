package com.dave.coding_interview_webapp.domain.entity.embeddable

import javax.persistence.Embeddable

/**
 *
 * Embeddable Address
 *
 * @author Alexwilfriedo
 */
@Embeddable
class Address(
    var primaryAddress: String = "",
    var secondaryAddress: String = "",
    var postalCode: String = "",
    var city: String = "",
    var country: String = "",
    var area: String = "",
    var department: String = "",
    var district: String = "",
    var borough: String = ""
)