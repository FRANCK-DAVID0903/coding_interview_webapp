package com.dave.coding_interview_webapp

import com.dave.coding_interview_webapp.application.bootstrap.ActivitySectorBootstrap
import com.dave.coding_interview_webapp.application.bootstrap.ActuatorBootstrap
import com.dave.coding_interview_webapp.application.bootstrap.RoleBootstrap
import com.dave.coding_interview_webapp.domain.account.port.RoleDomain
import com.dave.coding_interview_webapp.domain.account.port.UserDomain
import com.dave.coding_interview_webapp.domain.activity_sector.port.ActivitySectorDomain
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
        activitySectorDomain: ActivitySectorDomain

            ): CommandLineRunner {

        return CommandLineRunner {
            RoleBootstrap.seed(roleDomain)
            ActuatorBootstrap.seed(roleDomain,userDomain)
            ActivitySectorBootstrap.seed(activitySectorDomain)

        }
    }

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder.build()

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
