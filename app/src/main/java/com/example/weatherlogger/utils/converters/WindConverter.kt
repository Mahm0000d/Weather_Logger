package com.example.weatherlogger.utils.converters

import androidx.room.TypeConverter
import com.example.weatherlogger.data.model.WeatherItem
import com.google.gson.Gson

class WindConverter {
    @TypeConverter
    fun appToString(wind: WeatherItem.Wind): String = Gson().toJson(wind)

    @TypeConverter
    fun stringToApp(string: String): WeatherItem.Wind = Gson().fromJson(string, WeatherItem.Wind::class.java)

}