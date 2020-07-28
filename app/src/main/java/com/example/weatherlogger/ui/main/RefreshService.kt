package com.example.weatherlogger.ui.main
import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.weatherlogger.data.Repository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class RefreshService
    () : IntentService("RefreshService") {
    @Inject
    lateinit var repository: Repository
    override fun onHandleIntent(intent: Intent?) {
        Log.d("MainActivity","oggggggg")
        CoroutineScope(Dispatchers.Default).launch {
        val data=repository.allListData()
        for (weatherItem in data){
                repository.getRemoteWeatherData(weatherItem.coord.lat,weatherItem.coord.lon)
            }
        }
    }

}