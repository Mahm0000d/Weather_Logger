package com.example.weatherlogger.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherlogger.data.Repository
import com.example.weatherlogger.data.model.WeatherItem
import com.mindorks.framework.mvvm.utils.NetworkHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class DetailsViewModel @ViewModelInject constructor(
    private val repository: Repository
   // private val networkHelper: NetworkHelper
): ViewModel() {


     fun getItemDetails(id:Int):LiveData<WeatherItem>{

        return repository.getItemData(id)

    }

}