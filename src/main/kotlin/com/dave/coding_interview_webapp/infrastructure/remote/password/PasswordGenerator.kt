package com.dave.coding_interview_webapp.infrastructure.remote.password

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.mashape.unirest.http.Unirest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class PasswordGenerator {

    private val objectMapper: ObjectMapper = ObjectMapper()

    object Endpoint {
        const val CREATE_ENDPOINT = "https://api.motdepasse.xyz/create/"
    }

    fun generatePassword(): String {
        var password = "open"
        try {
            val response = Unirest.get(Endpoint.CREATE_ENDPOINT)
                    .queryString("password_length", 12)
                    .queryString("include_digits", "")
                    .queryString("include_lowercase", "")
                    .queryString("include_uppercase", "")
                    .queryString("quantity", "1")
                    .asString()

            if (response.status == HttpStatus.OK.value()) {
                val data = objectMapper.readValue(response.body, Response::class.java)
                if (data.passwords.isNotEmpty()) {
                    password = data.passwords[0]
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return password
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private class Response {
        var passwords: List<String> = mutableListOf()
    }
}