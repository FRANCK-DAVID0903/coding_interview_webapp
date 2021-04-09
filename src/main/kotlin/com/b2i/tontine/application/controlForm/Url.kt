package com.b2i.tontine.application.controlForm

import java.net.MalformedURLException
import java.net.URL
import javax.servlet.http.HttpServletRequest
import kotlin.jvm.Throws


object Url {
    @Throws(MalformedURLException::class)
    fun getURLBase(request: HttpServletRequest): String? {
        val requestURL = URL(request.requestURL.toString())
        val port = if (requestURL.port == -1) "" else ":" + requestURL.port
        return requestURL.protocol.toString() + "://" + requestURL.host + port
    }
}