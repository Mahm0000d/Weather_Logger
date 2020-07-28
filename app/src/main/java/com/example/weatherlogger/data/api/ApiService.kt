package com.example.weatherlogger.data.api

import com.example.weatherlogger.data.model.WeatherItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun getWeatherByCityName(@Query("appid") apiKey: String?,@Query("q") cityName: String?): Response<WeatherItem>

    @GET("weather")
    suspend fun getWeatherByLocation(@Query("appid") apiKey: String?,@Query("lat") lat: Double?,@Query("lon") lon: Double?): Response<WeatherItem>
}