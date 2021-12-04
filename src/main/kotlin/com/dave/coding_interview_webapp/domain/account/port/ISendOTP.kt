package com.b2i.projectName.domain.account.port

import com.dave.coding_interview_webapp.utils.OperationResult

/**
 * @author Alexwilfriedo
 **/
interface ISendOTP {

    fun sendOTP(receiver: String, otp : String): OperationResult<Boolean>
}