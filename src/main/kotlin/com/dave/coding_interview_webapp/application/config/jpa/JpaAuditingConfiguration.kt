package com.dave.coding_interview_webapp.application.config.jpa

import com.dave.coding_interview_webapp.application.config.jpa.AuditorAwareImplementation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
class JpaAuditingConfiguration {

    @Bean
    fun auditorAware(): AuditorAware<String> = AuditorAwareImplementation()
}