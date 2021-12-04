package com.dave.coding_interview_webapp.application.common

import com.fasterxml.jackson.annotation.JsonProperty


class FCMCollapseMessage(name: String, to: String, data: Map<String, String>,
                         @field:JsonProperty(value = "collapse_key") val collapseKey: String) : FCMMessage(name, to, data)