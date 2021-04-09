package com.b2i.tontine.infrastructure.remote.sms

import com.b2i.tontine.infrastructure.remote.sms.dto.HttpSMS
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

/**
 * @author alexwilfriedo
 */
@Service
class AroliSMSService(private val restTemplate: RestTemplate) {

    fun sendTo(message: HttpSMS): ResponseEntity<String> {
        val responseEntity = restTemplate.getForEntity(String.format(HTTP_SMS_API_BASE_URL, "senderv2.php", message.toGetParamString()), String::class.java)

        println(responseEntity.statusCode)
        println(responseEntity.body)

        return responseEntity
    }

    companion object {

        private val HTTP_SMS_API_BASE_URL = "http://gateway2.arolitec.com/interface/%s?%s"
    }
}
