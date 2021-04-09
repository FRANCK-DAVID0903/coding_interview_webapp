package com.b2i.tontine.application.config.jpa

import org.springframework.context.annotation.Bean
import org.springframework.data.domain.AuditorAware
import java.util.*

/**
 * Default JPA Auditor provider
 *
 * @author alexwilfriedo
 */
class AuditorAwareImplementation : AuditorAware<String> {

    @Bean
    override fun getCurrentAuditor(): Optional<String> = Optional.of("")
}