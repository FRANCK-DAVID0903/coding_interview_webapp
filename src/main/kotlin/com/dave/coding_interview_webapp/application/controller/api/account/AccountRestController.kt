package com.dave.coding_interview_webapp.application.controller.api.account

import com.dave.coding_interview_webapp.application.common.ResponseBody
import com.dave.coding_interview_webapp.application.common.ResponseSummary
import com.dave.coding_interview_webapp.application.controller.RestControllerEndpoint
import com.dave.coding_interview_webapp.domain.account.port.UserDomain
import com.dave.coding_interview_webapp.domain.client.port.ClientDomain
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.collections.HashMap

@RestController
@RequestMapping(RestControllerEndpoint.API_ACCOUNT_ENDPOINT)
class AccountRestController(
        private val clientDomain: ClientDomain,
        private val messageSource: MessageSource,
        private val eventPublisher: ApplicationEventPublisher,
        private val userDomain: UserDomain
) {

    @PostMapping(value = ["/authenticate"])
    fun authenticateClient(
            @RequestParam("username") username: String,
            @RequestParam("password") password: String,
            locale: Locale
    ): ResponseEntity<ResponseBody<HashMap<String, Any>>> {

        var errors: MutableMap<String, String> = mutableMapOf()
        var data: HashMap<String,Any> = HashMap()

        var optionalRole: Optional<String> = Optional.empty()

        if (username.isEmpty()) {
            errors["username"] = "username requis"
        }

        if (password.isEmpty()) {
            errors["password"] = "mot de passe requis"
        }

        val user = userDomain.findByUsername(username).orElse(null)

        if (user == null){
            errors["user"] = "utilisateur introuvable"
        }
        else{
            if (errors.isEmpty()) {
                val result = clientDomain.authenticateClient(username, password)
                if (result.errors!!.isEmpty()){
                    data["user"] = user
                } else{
                    data["msg"]="Login et mot de passe incorrect"
                    errors["user"] = "utilisateur introuvable"
                }

            }
        }

        return ResponseEntity(ResponseBody(data, ResponseSummary(errors)), HttpStatus.OK)
    }
}