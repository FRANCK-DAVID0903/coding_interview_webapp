package com.b2i.projectName.utils.helper

class TimeHelper {

    fun toCompleteTime(secondsCount: Long): String {

        val second = secondsCount % 60

        val minutesCount = (secondsCount - second) / 60

        val minute = minutesCount % 60

        val hoursCount = (minutesCount - minute) / 60

        return "$hoursCount h : $minute m : $second s"
    }
}