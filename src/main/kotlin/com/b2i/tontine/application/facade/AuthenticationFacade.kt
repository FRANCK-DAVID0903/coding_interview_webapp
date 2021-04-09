package com.b2i.tontine.application.facade

import com.b2i.tontine.domain.account.entity.User
import com.b2i.tontine.domain.account.port.UserDomain
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

/**
 * @author alexwilfriedo
 */
@Component
class AuthenticationFacade @Autowired
constructor(
    private val userDomain: UserDomain
) {

    fun getAuthenticatedUser(): Optional<User> {
        var optionalUser: Optional<User> = Optional.empty()

        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null && authentication !is AnonymousAuthenticationToken) {
            optionalUser = userDomain.findByUsername(authentication.name)
        }

        return optionalUser
    }

    fun getAuthenticatedUsername(): Optional<String> {
        var username: String? = null

        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null && authentication !is AnonymousAuthenticationToken) {
            username = authentication.name
        }
        return Optional.ofNullable(username)
    }


    fun getAuthenticatedUsers(): String? {
        var username: String? = null

        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null && authentication !is AnonymousAuthenticationToken) {
            username = authentication.name
        }
        return username
    }

}
