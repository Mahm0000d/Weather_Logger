package com.example.weatherlogger.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherlogger.data.model.WeatherItem
import com.example.weatherlogger.utils.converters.*

@Database(entities = [WeatherItem::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class,CloudsConverter::class,CoordConverter::class,
    SysConverter::class,WeatherConverter::class,WindConverter::class,MainConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun WeatherDao(): WeatherDao

}