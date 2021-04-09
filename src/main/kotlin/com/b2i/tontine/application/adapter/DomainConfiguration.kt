package com.b2i.tontine.application.adapter

import com.b2i.tontine.domain.account.port.ConnectionDomain
import com.b2i.tontine.domain.account.port.IAuthenticateUser
import com.b2i.tontine.domain.account.port.UserDomain
import com.b2i.tontine.domain.account.worker.ConnectionWorker
import com.b2i.tontine.domain.account.worker.UserWorker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Alexwilfriedo
 **/
@Configuration
class DomainConfiguration {

    @Bean
    fun userDomain(): UserDomain = UserWorker()

    @Bean
    fun connectionDomain(): ConnectionDomain = ConnectionWorker()

    @Bean
    fun authenticationManager(): IAuthenticateUser = UserWorker()

}