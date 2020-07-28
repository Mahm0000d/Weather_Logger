package com.example.weatherlogger.data
import androidx.lifecycle.LiveData
import com.example.weatherlogger.data.api.ApiHelper
import com.example.weatherlogger.data.model.WeatherItem
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class Repository @Inject constructor(private val apiHelper: ApiHelper,private val weatherDao: WeatherDao) {
      // constructor()
   suspend fun getRemoteWeatherData (lat:Double,lang:Double):Response<WeatherItem> {
      val response:Response<WeatherItem> =apiHelper.getWeatherByLocation(lat,lang)
       if(response.isSuccessful) {
           response.body()!!.date= Date()
           insertItem(response.body())
       }

      return response
   }

    suspend fun getRemoteWeatherData (name:String):Response<WeatherItem> {
        var response:Response<WeatherItem> =apiHelper.getWeatherByCityName(name)
        if(response.isSuccessful) {
            response.body()!!.date= Date()
            insertItem(response.body())
        }
        return response
    }

  val allData:LiveData<List<WeatherItem>> = weatherDao.getAllData()
  suspend fun allListData() =weatherDao.getWeatherListData()

  fun getItemData(id:Int):LiveData<WeatherItem> = weatherDao.getItemData(id)
   suspend fun deleteItem(id:Int){
       weatherDao.deleteItem(id)
    }

    suspend fun insertItem(weatherItem:WeatherItem?){
        weatherDao.insertAll(weatherItem!!)
    }




}