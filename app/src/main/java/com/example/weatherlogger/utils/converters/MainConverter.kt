package com.example.weatherlogger.utils.converters

import androidx.room.TypeConverter
import com.example.weatherlogger.data.model.WeatherItem
import com.google.gson.Gson

class MainConverter {
    @TypeConverter
    fun appToString(main: WeatherItem.Main): String = Gson().toJson(main)

    @TypeConverter
    fun stringToApp(string: String): WeatherItem.Main = Gson().fromJson(string, WeatherItem.Main::class.java)
}