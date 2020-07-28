package com.example.weatherlogger

import androidx.lifecycle.MutableLiveData
import com.example.weatherlogger.data.Repository
import com.example.weatherlogger.data.model.WeatherItem
import com.example.weatherlogger.ui.main.MainViewModel
import com.mindorks.framework.mvvm.utils.NetworkHelper
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.util.*
import kotlin.collections.ArrayList

class MainViewModelTest {

    @Test
    fun WeatherDataTest() {
        val repository: Repository = mock(verboseLogging = true)
        val networkHelper:NetworkHelper= mock(verboseLogging = true)
        whenever(repository.allData)
            .thenReturn(
                MutableLiveData(
                    arrayListOf(
                        createWeatherItem(1),
                        createWeatherItem(2),
                        createWeatherItem(3)
                    )
                )
            )
        val expected = 3
        val model = MainViewModel(repository,networkHelper)

        val todos = model.WeatherData.value

        Assert.assertNotNull(todos)
        Assert.assertEquals(expected, todos!!.size)
    }



    fun createWeatherItem(id:Int): WeatherItem {
        var weather= ArrayList<WeatherItem.Weather>()
        weather.add(WeatherItem.Weather("clear"))
        return WeatherItem(id,"cairo", WeatherItem.Coord(5.0,5.0),weather ,
            WeatherItem.Main(20.0,10.0,10.0,50,50),
            WeatherItem.Wind(50.0,20), WeatherItem.Clouds(10), WeatherItem.Sys("eg"), Date()
        )
    }

}