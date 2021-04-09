package com.b2i.tontine.application.common

import java.util.*

/**
 * @author alexwilfriedo
 */
class ResponseSummary(val errors: Map<String, String>? = null) {

    val isSuccess: Boolean = errors == null || errors.isEmpty()

    constructor(error: String) : this(object : HashMap<String, String>() {
        init {
            put("message", error)
        }
    })
}