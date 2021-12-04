package com.dave.coding_interview_webapp.application.resolver

import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer


/**
 * Spring MVC {@link HandlerMethodArgumentResolver} that resolves @Controller MethodParameters of type {@link SessionToken}
 *
 * @author Alexwilfriedo
 */
class SessionTokenHandlerArgumentResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(SessionToken::class.java) != null
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): String? {

        return webRequest.getHeader("Authorization")?.trim()
    }

}