package com.example.weatherlogger.utils.converters

import androidx.room.TypeConverter
import com.example.weatherlogger.data.model.WeatherItem
import com.google.gson.Gson

class WeatherConverter {
    @TypeConverter
    fun listToJson(value: List<WeatherItem.Weather>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) :List<WeatherItem.Weather> = Gson().fromJson(value, Array<WeatherItem.Weather>::class.java).toList()
}