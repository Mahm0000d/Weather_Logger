package com.example.weatherlogger

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherlogger.data.Repository
import com.example.weatherlogger.data.WeatherDao
import com.example.weatherlogger.data.WeatherDatabase
import com.example.weatherlogger.data.api.ApiHelper
import com.example.weatherlogger.data.api.ApiHelperImpl
import com.example.weatherlogger.data.api.ApiService
import com.example.weatherlogger.data.model.WeatherItem
import com.example.weatherlogger.data.model.WeatherItem.Coord
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import java.util.*
import kotlin.collections.ArrayList


@RunWith(AndroidJUnit4::class)
class RepositoryTest {
    private lateinit var db: WeatherDatabase
    private lateinit var  apiHelper:ApiHelper
    private lateinit var apiService:ApiService
    @get:Rule
    val testRule = InstantTaskExecutorRule()
    @get:Rule
    val exceptionRule = ExpectedException.none()
    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, WeatherDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
    @Test
    fun insertWeatherItemTest() {
        runBlocking {
            val dao = mock<WeatherDao>()
            val apiHelper: ApiHelper = mock()
            val repo = Repository(apiHelper, dao)
            val expected = createWeatherItem(1)

            repo.insertItem(expected)

            argumentCaptor<WeatherItem>()
                .apply {
                    verify(dao).insertAll(capture())
                    assertEquals(expected, firstValue)
                }
        }
    }
    @Test
   fun getAllWeatherItemsTest(){
       runBlocking {
           val apiHelper: ApiHelper = mock()
           val weatherItemTest = createWeatherItem(3)
           db.WeatherDao().insertAll(weatherItemTest)
           db.WeatherDao().insertAll(createWeatherItem(2))
           db.WeatherDao().insertAll(createWeatherItem(1))
           val dao = spy(db.WeatherDao())
           val repo = Repository(apiHelper, dao)
           val expected = 3

           val actual = repo.allData.test().value()

           assertEquals(expected, actual.size)
           verify(dao).getAllData()
       }
   }
    @Test
    fun getItemDataTest(){
        val apiHelper: ApiHelper = mock()
        val dao = mock<WeatherDao> ()
        val repo = Repository(apiHelper, dao)
        val id = 1

        repo.getItemData(id)

        verify(dao).getItemData(id)
    }

    @Test
    fun deleteItemTest(){
        runBlocking {
            val apiHelper: ApiHelper = mock()
            val dao = mock<WeatherDao>()
            val repo = Repository(apiHelper, dao)
            val id = 1

            repo.deleteItem(id)

            verify(dao).deleteItem(id)
        }
    }


    @After
    fun teardown() {
        db.close()
    }

    fun createWeatherItem(id:Int):WeatherItem{
        var weather= ArrayList<WeatherItem.Weather>()
        weather.add(WeatherItem.Weather("clear"))
        return WeatherItem(id,"cairo",Coord(5.0,5.0),weather ,
            WeatherItem.Main(20.0,10.0,10.0,50,50),
            WeatherItem.Wind(50.0,20), WeatherItem.Clouds(10), WeatherItem.Sys("eg"),Date())
    }



}