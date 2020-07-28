package com.example.weatherlogger.utils.converters

import androidx.room.TypeConverter
import com.example.weatherlogger.data.model.WeatherItem
import com.google.gson.Gson

class SysConverter {
    @TypeConverter
    fun appToString(sys: WeatherItem.Sys): String = Gson().toJson(sys)

    @TypeConverter
    fun stringToApp(string: String): WeatherItem.Sys = Gson().fromJson(string, WeatherItem.Sys::class.java)
}