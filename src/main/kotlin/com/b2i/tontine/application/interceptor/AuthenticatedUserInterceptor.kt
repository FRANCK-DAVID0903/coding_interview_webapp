package com.b2i.tontine.application.interceptor

import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.domain.account.entity.UserProfiler
import com.b2i.tontine.domain.notification.port.NotificationDomain
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
    private val authenticationFacade: AuthenticationFacade,
    private val notificationDomain: NotificationDomain
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

                //On vas ajouter les notifications aux pages
                val notifsUser = notificationDomain.findAllByUserAndState(user,0)
                val nbNotif = notifsUser.count()
                modelAndView.addObject("allsNotifs",notifsUser)
                modelAndView.addObject("nbNotif",nbNotif)
            }
        }
    }
}