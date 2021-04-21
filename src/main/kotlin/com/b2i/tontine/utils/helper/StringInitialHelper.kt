package com.b2i.tontine.utils.helper


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/21
 * @Time: 10:39
 */
class StringInitialHelper {

    fun getStringWordsInitials(str: String): String {
        var result = ""

        var v = true

        for (i in str.indices) {
            // If it is space, set v as true.
            if (str[i] == ' ') {
                v = true
            }
            // Else check if v is true or not.
            // If true, copy character in output
            // string and set v as false.
            else if (str[i] != ' ' && v) {
                result += (str[i])
                v = false
            }
        }

        return result.toUpperCase()
    }

}