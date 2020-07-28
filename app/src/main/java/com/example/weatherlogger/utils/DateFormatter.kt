package com.example.weatherlogger.utils

import java.text.SimpleDateFormat
import java.util.*

object  DateFormatter {
    fun formatDate(date: Date):String{
//        val date: Date =
//            SimpleDateFormat("dd-MM-yyyy hh:mm").parse(d)
        val formattedDate: String = SimpleDateFormat("dd-MM-yyyy hh:mm").format(date)
        return formattedDate
    }
}