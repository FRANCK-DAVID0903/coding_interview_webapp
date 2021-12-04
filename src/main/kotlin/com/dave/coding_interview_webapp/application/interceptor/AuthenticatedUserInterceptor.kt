package com.dave.coding_interview_webapp.application.interceptor

import com.dave.coding_interview_webapp.application.facade.AuthenticationFacade
import com.dave.coding_interview_webapp.domain.account.entity.UserProfiler
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView
import org.springframework.web.servlet.view.UrlBasedViewResolver
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticatedUserInterceptor constructor(
    private val authenticationFacade: AuthenticationFacade
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        if (modelAndView == null) {
            return
        }

        if (modelAndView.view !is RedirectView && !modelAndView.viewName?.contains(UrlBasedViewResolver.REDIRECT_URL_PREFIX)!!) {
            val user = authenticationFacade.getAuthenticatedUser().orElse(null)

            Optional.ofNullable(user).ifPresent {
                val profile = UserProfiler.profile(it)
                modelAndView.addObject("user", profile)
            }
        }
    }
}