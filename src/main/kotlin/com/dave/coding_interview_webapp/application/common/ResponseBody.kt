package com.dave.coding_interview_webapp.application.common


class ResponseBody<T>(val data: T? = null, val summary: ResponseSummary?) {

    constructor(summary: ResponseSummary?) : this(null, summary)
}