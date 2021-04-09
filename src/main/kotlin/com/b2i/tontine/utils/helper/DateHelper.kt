package com.b2i.tontine.utils.helper

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*


class DateHelper(date: Date = Date()) {

    companion object {
        val simpleDateTimeFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").apply { timeZone = TimeZone.getTimeZone(ZoneOffset.UTC) }

        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy").apply { timeZone = TimeZone.getTimeZone(ZoneOffset.UTC) }

        val englishSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd").apply { timeZone = TimeZone.getTimeZone(ZoneOffset.UTC) }
    }

    var dayOfMonth: Int = 0

    var monthOfYear: Int = 0

    var year: Int = 0

    var dayOfWeek: Int = 0

    var hour: Int = 0

    var minute: Int = 0

    var second: Int = 0

    var monthName: String = ""

    var dateFormat: String = ""

    var weekOfMonth: Int = 0

    init {
        val localDateTime = Timestamp(date.time).toLocalDateTime().atZone(ZoneId.from(ZoneOffset.UTC))

        val calendar = Calendar.getInstance()
        calendar.clear()
        calendar.set(localDateTime.year, localDateTime.monthValue - 1, localDateTime.dayOfMonth)

        dayOfMonth = localDateTime.dayOfMonth
        monthOfYear = localDateTime.monthValue
        year = localDateTime.year
        dayOfWeek = localDateTime.dayOfWeek.value
        weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
        hour = localDateTime.hour
        minute = localDateTime.minute
        second = localDateTime.second
        monthName = localDateTime.month.name
        dateFormat = localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    fun format(date: LocalDate?) = (if (date != null) simpleDateTimeFormat.format(date) else "-")!!

    fun format(date: Date?) = (if (date != null) simpleDateTimeFormat.format(date) else "-")!!

    fun formatWithoutTime(date: Date?) = (if (date != null) simpleDateFormat.format(date) else "-")!!

    fun dayOfWeekFrench(day:Int):String{
        when (day) {
            1 -> {
                return "Lundi"
            }
            2 -> {
                return  "Mardi"
            }
            3 -> {
                return "Mercedi"
            }
            4 -> {
                return "Jeudi"
            }
            5 -> {
                return "Vendredi"
            }
            6 -> {
                return "Samedi"
            }
            7 -> {
                return "Dimanche"
            }
            else -> {
                return ""
            }
        }
    }


    fun monthFrench(month:Int):String{
        return when(month){
            1-> "Janvier"
            2-> "Fevrier"
            3-> "Mars"
            4-> "Avril"
            5-> "Mai"
            6-> "Juin"
            7-> "Juillet"
            8-> "Août"
            9-> "Septembre"
            10-> "Octobre"
            11-> "Novembre"
            12-> "Décembre"
            else-> ""
        }

    }

}