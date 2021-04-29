package com.b2i.tontine.utils.helper

import com.b2i.tontine.domain.tontine.entity.TontinePeriodicityType
import com.b2i.tontine.domain.tontine.entity.TontineType


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/24
 * @Time: 21:21
 */
class ObjectHelper {

    fun getTontineType(str: String): String {
        return when (str) {
            "OPENED" -> TontineType.OPENED
            "CLOSED" -> TontineType.CLOSED
            else -> TontineType.CLOSED
        }
    }

    fun getTontinePeriodicity(str: String): String {
        return when (str) {
            "WEEKLY" -> TontinePeriodicityType.WEEKLY
            "MONTHLY" -> TontinePeriodicityType.MONTHLY
            "ANNUAL" -> TontinePeriodicityType.ANNUAL
            else -> TontinePeriodicityType.MONTHLY
        }
    }

}