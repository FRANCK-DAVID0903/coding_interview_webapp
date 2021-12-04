package com.b2i.projectName.utils.helper

object StringHelper {

    fun clean(data: String) = data.replace("\'", "\\\'")
}