package com.b2i.tontine.application.controller

object RestControllerEndpoint {

    const val API_BASE_URL = "api/v1"

    const val API_BASE_SECURED_URL = "api/secured/v1"

    const val API_USER_ACCOUNT = "${API_BASE_URL}/account"

    const val API_CLIENT = "${API_BASE_URL}/client"

    const val API_ENTERPRISE_READ_FORMS_REQUEST_MAPPING = "/enterprise/forms"

    const val API_FORM_RESPONSE_SUBMIT_REQUEST_MAPPING = "/enterprise/form/{id}/submit"
}