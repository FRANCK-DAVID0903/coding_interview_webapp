package com.dave.coding_interview_webapp.application.common


open class FCMMessage(val name: String, val to: String, val data: Map<String, String>)