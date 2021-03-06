package com.dave.coding_interview_webapp.application.controller

import com.dave.coding_interview_webapp.application.facade.AuthenticationFacade
import com.b2i.projectName.utils.helper.DateHelper
import com.b2i.projectName.utils.helper.ObjectHelper
import com.b2i.projectName.utils.helper.StringInitialHelper
import org.springframework.beans.factory.annotation.Autowired
import java.time.format.DateTimeFormatter


abstract class BaseController(private val templateBaseDir: String, private val routeBase: String) {

    protected val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    protected val dateHelper = DateHelper()

    protected val stringInitialHelper = StringInitialHelper()

    protected val objectHelper = ObjectHelper()

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
