package com.b2i.tontine.utils.helper

object StringHelper {

    fun clean(data: String) = data.replace("\'", "\\\'")
}