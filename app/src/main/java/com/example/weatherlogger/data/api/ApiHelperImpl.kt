package com.example.weatherlogger.data.api
import com.example.weatherlogger.data.model.WeatherItem
import com.example.weatherlogger.utils.Constants
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getWeatherByCityName(name:String): Response<WeatherItem> = apiService.getWeatherByCityName(Constants.API_KEY,name)
    override suspend fun getWeatherByLocation(lat: Double, lan: Double): Response<WeatherItem> = apiService.getWeatherByLocation(Constants.API_KEY,lat,lan)

}