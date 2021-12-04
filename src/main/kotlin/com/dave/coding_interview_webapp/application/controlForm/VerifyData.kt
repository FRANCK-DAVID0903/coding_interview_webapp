package com.dave.coding_interview_webapp.application.controlForm

import java.util.regex.Matcher
import java.util.regex.Pattern

object VerifyData
{
    val VALID_EMAIL_ADDRESS_REGEX: Pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

    fun VerifyIsEmail(emailStr: String?): Boolean
    {
        val matcher: Matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr.toString())
        return matcher.find()
    }
}