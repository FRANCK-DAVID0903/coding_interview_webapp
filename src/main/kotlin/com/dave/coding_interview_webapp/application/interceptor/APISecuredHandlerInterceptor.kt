package com.dave.coding_interview_webapp.application.interceptor

import com.b2i.projectName.application.Constant
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class APISecuredHandlerInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        super.preHandle(request, response, handler)

        val token = request.getHeader("Authorization")

        if (!isClientAuthorize(token.replace("Bearer ", "").trim())) {
            response.status = HttpStatus.FORBIDDEN.value()
            response.writer.write("Forbidden Access")
            return false
        }

        request.session.setAttribute(Constant.SESSION_TOKEN_KEY, token)

        return true
    }

    private fun isClientAuthorize(token: String?): Boolean {
        if (token == null || token.isEmpty()) return false

        return true
    }
}