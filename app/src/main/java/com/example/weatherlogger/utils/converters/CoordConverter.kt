package com.example.weatherlogger.utils.converters

import androidx.room.TypeConverter
import com.example.weatherlogger.data.model.WeatherItem
import com.google.gson.Gson

class CoordConverter {
    @TypeConverter
    fun appToString(coord: WeatherItem.Coord): String = Gson().toJson(coord)

    @TypeConverter
    fun stringToApp(string: String): WeatherItem.Coord = Gson().fromJson(string, WeatherItem.Coord::class.java)
}