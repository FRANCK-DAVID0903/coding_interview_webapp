package com.b2i.tontine

import com.b2i.tontine.application.bootstrap.ActuatorBootstrap
import com.b2i.tontine.application.bootstrap.RoleBootstrap
import com.b2i.tontine.domain.account.port.RoleDomain
import com.b2i.tontine.domain.account.port.UserDomain
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class Application {

    @Bean
    fun initDatabase(
            roleDomain: RoleDomain,
            userDomain: UserDomain,

            ): CommandLineRunner {

        return CommandLineRunner {
            RoleBootstrap.seed(roleDomain)
            ActuatorBootstrap.seed(roleDomain,userDomain)

        }
    }

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder.build()

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
