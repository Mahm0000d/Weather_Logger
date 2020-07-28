package com.example.weatherlogger.di

import android.content.Context
import androidx.room.Room
import com.example.weatherlogger.BuildConfig
import com.example.weatherlogger.data.WeatherDao
import com.example.weatherlogger.data.WeatherDatabase
import com.example.weatherlogger.data.api.ApiHelper
import com.example.weatherlogger.data.api.ApiHelperImpl
import com.example.weatherlogger.data.api.ApiService
import com.example.weatherlogger.utils.Constants

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL



    @Provides
    @Singleton
    fun provideRetrofit(
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(OkHttpClient())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): WeatherDatabase {
        return Room.databaseBuilder(
            appContext,
            WeatherDatabase::class.java,
            "weatherdb"
        ).build()
    }

    @Provides
    fun provideTodoDao(database: WeatherDatabase): WeatherDao {
        return database.WeatherDao()
    }

    @Provides
    fun provideExecuter(): Executor {
        return Executors.newSingleThreadExecutor()
    }

}