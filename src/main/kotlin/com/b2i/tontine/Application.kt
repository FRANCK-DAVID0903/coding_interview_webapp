package com.b2i.tontine

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

    ): CommandLineRunner {

        return CommandLineRunner {

        }
    }

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder.build()

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
