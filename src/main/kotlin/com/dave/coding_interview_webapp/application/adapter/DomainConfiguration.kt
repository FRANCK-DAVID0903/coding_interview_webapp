package com.dave.coding_interview_webapp.application.adapter

import com.dave.coding_interview_webapp.domain.account.port.ConnectionDomain
import com.dave.coding_interview_webapp.domain.account.port.IAuthenticateUser
import com.dave.coding_interview_webapp.domain.account.port.UserDomain
import com.dave.coding_interview_webapp.domain.account.worker.ConnectionWorker
import com.dave.coding_interview_webapp.domain.account.worker.UserWorker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class DomainConfiguration {

    @Bean
    fun userDomain(): UserDomain = UserWorker()

    @Bean
    fun connectionDomain(): ConnectionDomain = ConnectionWorker()

    @Bean
    fun authenticationManager(): IAuthenticateUser = UserWorker()

}