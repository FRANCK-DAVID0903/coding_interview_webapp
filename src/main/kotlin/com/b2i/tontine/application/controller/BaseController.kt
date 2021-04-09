package com.b2i.tontine.application.controller

import com.b2i.tontine.application.facade.AuthenticationFacade
import com.b2i.tontine.utils.helper.DateHelper
import org.springframework.beans.factory.annotation.Autowired
import java.time.format.DateTimeFormatter

/**
 * @author alexwilfriedo
 */
abstract class BaseController(private val templateBaseDir: String, private val routeBase: String) {

    protected val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    protected val dateHelper = DateHelper()

    constructor(templateBaseDir: String) : this(templateBaseDir, templateBaseDir)

    @Autowired
    private lateinit var authenticationFacade: AuthenticationFacade

    internal fun forwardTo(templatePath: String): String {
        return String.format("/%s/%s", templateBaseDir, templatePath)
    }

    internal fun redirectTo(path: String): String {
        return String.format("redirect:/%s/%s", routeBase, path)
    }
}
