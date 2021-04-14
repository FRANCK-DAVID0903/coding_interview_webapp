package com.b2i.tontine.application.config

import com.b2i.tontine.application.controller.RestControllerEndpoint
import com.b2i.tontine.application.interceptor.APIHandlerInterceptor
import com.b2i.tontine.application.interceptor.APISecuredHandlerInterceptor
import com.b2i.tontine.application.interceptor.AuthenticatedUserInterceptor
import com.b2i.tontine.application.interceptor.HelperInterceptor
import com.b2i.tontine.application.resolver.SessionTokenHandlerArgumentResolver
import com.b2i.tontine.infrastructure.local.storage.StorageService
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.handler.MappedInterceptor
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import java.util.*


/**
 * @author alexwilfriedo
 */
@Configuration
class WebMvcConfig constructor(
    private val authenticatedUserInterceptor: AuthenticatedUserInterceptor,
    private val helperInterceptor: HelperInterceptor,
    private val apiHandlerInterceptor: APIHandlerInterceptor,
    private val apiSecuredHandlerInterceptor: APISecuredHandlerInterceptor
) : WebMvcConfigurer {

    @Bean
    fun deviceResolverHandlerInterceptor(): DeviceResolverHandlerInterceptor {
        return DeviceResolverHandlerInterceptor()
    }

    @Bean
    fun deviceHandlerMethodArgumentResolver(): DeviceHandlerMethodArgumentResolver {
        return DeviceHandlerMethodArgumentResolver()
    }

    @Bean
    fun sessionHandlerMethodArgumentResolver(): SessionTokenHandlerArgumentResolver {
        return SessionTokenHandlerArgumentResolver()
    }

    @Bean
    fun localeResolver(): LocaleResolver {
        val localeResolver = AcceptHeaderLocaleResolver()
        localeResolver.defaultLocale = Locale.FRENCH
        return localeResolver
    }

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
        val localeChangeInterceptor = LocaleChangeInterceptor()
        localeChangeInterceptor.paramName = "lang"
        return localeChangeInterceptor
    }

    @Bean
    fun messageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:locale/messages")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor())
        registry.addInterceptor(deviceResolverHandlerInterceptor())
        registry.addInterceptor(
            MappedInterceptor(
                arrayOf("/${RestControllerEndpoint.API_BASE_SECURED_URL}/**"),
                apiSecuredHandlerInterceptor
            )
        )
        registry.addInterceptor(
            MappedInterceptor(
                arrayOf("/${RestControllerEndpoint.API_BASE_URL}/**"),
                apiHandlerInterceptor
            )
        )
        registry.addInterceptor(MappedInterceptor(arrayOf("/dashboard/**","/**"), authenticatedUserInterceptor))
        registry.addInterceptor(MappedInterceptor(arrayOf("/**"), helperInterceptor))
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
    }


    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {

        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/")

        registry.addResourceHandler("/${StorageService.uploadDirectory}/**")
            .addResourceLocations(StorageService.uploadLocation)

    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(deviceHandlerMethodArgumentResolver())
        resolvers.add(sessionHandlerMethodArgumentResolver())
    }
}