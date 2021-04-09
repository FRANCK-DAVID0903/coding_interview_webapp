package com.b2i.tontine.application.common

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author alexwilfriedo
 */
class FCMCollapseMessage(name: String, to: String, data: Map<String, String>,
                         @field:JsonProperty(value = "collapse_key") val collapseKey: String) : FCMMessage(name, to, data)