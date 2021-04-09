package com.b2i.tontine.domain.entity.embeddable

import javax.persistence.Embeddable

/**
 * Embeddable Contact
 *
 * @author Alexwilfriedo
 */
@Embeddable
class Contact(
    var phone: String = "",
    var mobile: String = "",
    var fax: String = "",
    var email: String = ""
)