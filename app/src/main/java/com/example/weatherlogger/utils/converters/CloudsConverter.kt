package com.example.weatherlogger.utils.converters

import androidx.room.TypeConverter
import com.example.weatherlogger.data.model.WeatherItem
import com.google.gson.Gson

class CloudsConverter {
    @TypeConverter
    fun appToString(cloud: WeatherItem.Clouds): String = Gson().toJson(cloud)

    @TypeConverter
    fun stringToApp(string: String): WeatherItem.Clouds = Gson().fromJson(string, WeatherItem.Clouds::class.java)

}
