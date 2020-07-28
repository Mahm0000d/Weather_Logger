package com.example.weatherlogger.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weatherlogger.utils.converters.DateConverter
import java.util.*

@Entity(tableName = "Weather")
class WeatherItem(
    @field:PrimaryKey var id: Int,
    var name: String,
    var coord:Coord,
    var weather: List<Weather>,
    var main: Main,
    var wind: Wind,
    var clouds: Clouds,
    var sys: Sys,
    var date: Date?
) {
    class Weather internal constructor(var description: String)

   class Main(
        var temp: Double,
        var temp_min: Double,
        var temp_max: Double,
        var pressure: Int,
        var humidity: Int
    )

    class Wind(var speed: Double, var deg: Int)
    class Coord(var lat: Double, var lon: Double)
    class Clouds(var clouds: Int)

    class Sys(var country: String)

}