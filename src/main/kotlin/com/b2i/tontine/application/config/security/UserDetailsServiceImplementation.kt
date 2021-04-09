package com.b2i.tontine.application.config.security

import com.b2i.tontine.domain.account.port.IAuthenticateUser
import com.b2i.tontine.domain.account.port.UserDomain
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

/**
 * @author alexwilfriedo
 */
@Service
class UserDetailsServiceImplementation @Autowired
constructor(private val authenticationManager: IAuthenticateUser) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {

        return authenticationManager.findByUsername(username).orElseThrow { UsernameNotFoundException("Invalid username and password") }
    }
}
