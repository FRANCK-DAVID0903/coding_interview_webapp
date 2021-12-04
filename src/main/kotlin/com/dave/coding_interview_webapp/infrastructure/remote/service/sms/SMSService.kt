package com.dave.coding_interview_webapp.infrastructure.remote.service.sms

import com.b2i.projectName.domain.account.port.ISendOTP
import com.dave.coding_interview_webapp.infrastructure.remote.sms.AroliSMSService
import com.dave.coding_interview_webapp.infrastructure.remote.sms.dto.HttpSMS
import com.dave.coding_interview_webapp.utils.OperationResult
import org.springframework.stereotype.Service

/**
 * @author Alexwilfriedo
 **/
@Service
class SMSService(private val smsService: AroliSMSService) : ISendOTP {

    override fun sendOTP(receiver: String, otp: String): OperationResult<Boolean> {
        val errors: MutableMap<String, String> = mutableMapOf()
        val sms = HttpSMS.Builder(receiver, "Votre code OTP est : $otp").setCharset("UTF-8").build()
        try {
            smsService.sendTo(sms)
        } catch (e: Exception) {
            errors["operation"] = e.localizedMessage
        }
        println(receiver)
        return OperationResult(errors.isEmpty(), errors)
    }
}