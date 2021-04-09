package com.b2i.tontine.application.common

/**
 * @author alexwilfriedo
 */
class ResponseBody<T>(val data: T? = null, val summary: ResponseSummary?) {

    constructor(summary: ResponseSummary?) : this(null, summary)
}