package com.example.weatherlogger.data.api

import com.example.weatherlogger.data.model.WeatherItem
import retrofit2.Response

interface ApiHelper {

    suspend fun getWeatherByCityName(name:String): Response<WeatherItem>
    suspend fun getWeatherByLocation(lat:Double,lan:Double): Response<WeatherItem>
}