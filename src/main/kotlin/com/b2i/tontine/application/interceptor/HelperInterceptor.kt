package com.b2i.tontine.application.interceptor

import com.b2i.tontine.infrastructure.local.storage.StorageService
import com.b2i.tontine.utils.helper.CurrencyHelper
import com.b2i.tontine.utils.helper.DateHelper
import com.b2i.tontine.utils.helper.StringHelper
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView
import org.springframework.web.servlet.view.UrlBasedViewResolver
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class HelperInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        return true
    }

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        if (modelAndView == null) {
            return
        }

        if (modelAndView.view !is RedirectView && !modelAndView.viewName?.contains(UrlBasedViewResolver.REDIRECT_URL_PREFIX)!!) {

            modelAndView.addObject("stringHelper", StringHelper)
            modelAndView.addObject("currencyHelper", CurrencyHelper)
            modelAndView.addObject("dateHelper", DateHelper())
            modelAndView.addObject("rootLocation", StorageService.uploadDirectory)
        }
    }
}