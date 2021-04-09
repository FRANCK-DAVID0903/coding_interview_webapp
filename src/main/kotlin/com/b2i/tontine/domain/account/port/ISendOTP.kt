package com.b2i.tontine.domain.account.port

import com.b2i.tontine.utils.OperationResult

/**
 * @author Alexwilfriedo
 **/
interface ISendOTP {

    fun sendOTP(receiver: String, otp : String): OperationResult<Boolean>
}