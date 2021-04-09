package com.b2i.tontine.infrastructure.remote.sms.dto

/**
 * @author Alexwilfriedo
 **/
class HttpSMS private constructor(
    private val sender: String? = null,
    private val receiver: String? = null,
    val content: String? = null,
    val dlurl: String? = null,
    val charset: String? = null,
    val timetosend: String? = null,
    val climsgid: String? = null,
    val numericsender: String? = null
) {

    private val user = "digital"

    private val password = "dgt@2017"

    class Builder {
        private val sender: String
        private val receiver: String
        private val content: String
        private var dlurl: String? = null
        private var charset: String? = null
        private var timetosend: String? = null
        private var climsgid: String? = null
        private var numericsender: String? = null

        constructor(sender: String, receiver: String, content: String) {
            this.sender = sender
            this.receiver = receiver
            this.content = content
        }

        constructor(receiver: String, content: String) {
            sender = "DoctoPlus"
            this.receiver = receiver
            this.content = content
        }

        fun setDlurl(dlurl: String?): Builder = this.apply { this.dlurl = dlurl }

        fun setCharset(charset: String?): Builder = this.apply { this.charset = charset }

        fun setTimetosend(timetosend: String?): Builder = this.apply { this.timetosend = timetosend }

        fun setClimsgid(climsgid: String?): Builder = this.apply { this.climsgid = climsgid }

        fun setNumericsender(numericsender: String?): Builder = this.apply { this.numericsender = numericsender }

        fun build(): HttpSMS {
            return HttpSMS(sender, receiver, content, dlurl, charset, timetosend, climsgid, numericsender)
        }
    }

    fun toGetParamString(): String? {
        return String.format(
            "user=%s&password=%s&sender=%s&receiver=%s&content=%s&charset=",
            user,
            password,
            sender,
            receiver,
            content,
            charset
        )
    }
}