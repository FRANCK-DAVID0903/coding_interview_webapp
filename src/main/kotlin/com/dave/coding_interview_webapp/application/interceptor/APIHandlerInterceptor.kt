package com.dave.coding_interview_webapp.application.interceptor

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class APIHandlerInterceptor : HandlerInterceptor {

    val availableTokens: Array<String> = arrayOf("Bearer 0KaXYgPTBBQURFQzE0MEYyMDE0OUI3MzDNzA1MzNCODk2OEIyNUMwOTk4MzM5MjJCQkCN0E1DQprZXk9OEE5QNwRDJCQkM5MkY2NjBERIM2QTYzQzhGNEE1MDMyc2FsdD0zRDlFREM4NkU0NjVQzM4QTVFOEQxOTcwOUIxQzZFOE")

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        super.preHandle(request, response, handler)

        val token = request.getHeader("Authorization")
        if (token == null || !availableTokens.contains(token) && request.session.getAttribute(token) == null) {
            response.status = HttpStatus.FORBIDDEN.value()
            response.writer.write("Forbidden Access")
            return false
        }

        return true
    }
}