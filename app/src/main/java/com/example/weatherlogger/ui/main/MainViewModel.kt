package com.example.weatherlogger.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherlogger.data.Repository
import com.example.weatherlogger.data.model.WeatherItem
import com.mindorks.framework.mvvm.utils.NetworkHelper
import com.mindorks.framework.mvvm.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper
): ViewModel() {
     val _latitude =MutableLiveData<kotlin.Double>()
     val _langtitude=MutableLiveData<kotlin.Double>()
    val _id =MutableLiveData<Int>()
    private val _errorText = MutableLiveData<String?>()
    val errorText: LiveData<String?>
        get() = _errorText

    private val _loadingSpinner = MutableLiveData<Boolean>(true)
    val loadingSpinner: LiveData<Boolean>
        get() =  _loadingSpinner

    private val _errorDialog = MutableLiveData<String>()
    val errorDialog: LiveData<String>
        get() =  _errorDialog


    val WeatherData:LiveData<List<WeatherItem>> =repository.allData

     fun fetchWeatherData(){
        viewModelScope.launch {
            _loadingSpinner.postValue(true)
            if (networkHelper.isNetworkConnected()) {
                val response = repository.getRemoteWeatherData(_latitude.value!!,_langtitude.value!!)
                if (!response.isSuccessful)
                    _errorDialog.postValue("City Not Found")

            }else
                _errorDialog.postValue("check your internet connection")
            _loadingSpinner.postValue(false)

        }
    }

    fun deleteWeatherItem(){
        viewModelScope.launch {
            repository.deleteItem(_id.value!!)
        }
    }



    fun serachByName(name:String){
        viewModelScope.launch {
            _loadingSpinner.postValue(true)
            if (networkHelper.isNetworkConnected()) {
                val response = repository.getRemoteWeatherData(name)
                if (!response.isSuccessful)
                    _errorDialog.postValue("City Not Found")
            }else
                _errorDialog.postValue("check your internet connection")
            _loadingSpinner.postValue(false)
        }
    }



}