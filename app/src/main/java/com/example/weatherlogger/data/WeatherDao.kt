package com.example.weatherlogger.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherlogger.data.model.WeatherItem
import retrofit2.http.DELETE

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(weather: WeatherItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAllTest(weather: WeatherItem)
    @Query("DELETE FROM Weather WHERE id= :id" )
    suspend fun deleteItem(id: Int)

    @Query("SELECT * FROM Weather " )
    fun getAllData():LiveData<List<WeatherItem>>

    @Query("SELECT * FROM Weather WHERE id=:id " )
    fun getItemData(id:Int):LiveData<WeatherItem>

    @Query("SELECT * FROM Weather " )
    suspend fun  getWeatherListData():List<WeatherItem>
}