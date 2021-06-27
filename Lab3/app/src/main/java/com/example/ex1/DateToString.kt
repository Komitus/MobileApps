package com.example.ex1

import java.time.LocalDate
import java.time.LocalTime

class DateToString {

    fun makeDateToString(localDate : LocalDate): String {
        return localDate.dayOfMonth.toString()+" "+localDate.month.name[0]+
                localDate.month.name[1]+localDate.month.name[2]+" "+localDate.year.toString()
    }
    fun makeTimeToString(localTime: LocalTime): String{
        val tmpHour : Int = localTime.hour
        val tmpMinute : Int = localTime.minute
        var hour : String = ""
        var minute : String = ""
        if(tmpHour<10) hour="0"
        if(tmpMinute<10) minute="0"
        hour+=tmpHour
        minute+=tmpMinute

        return hour+":"+minute
    }
}