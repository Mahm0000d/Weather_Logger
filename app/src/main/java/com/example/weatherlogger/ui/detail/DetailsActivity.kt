package com.example.weatherlogger.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.weatherlogger.R
import com.example.weatherlogger.databinding.ActivityDetailsBinding
import com.example.weatherlogger.ui.detail.DetailsViewModel
import com.example.weatherlogger.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class DetailsActivity : AppCompatActivity() {
    private val detailsViewModel : DetailsViewModel by viewModels()
    lateinit var activityDetailsBinding:ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailsBinding=DataBindingUtil.setContentView(this,R.layout.activity_details)
        setUpDetailsObserver()
    }
    private fun setUpDetailsObserver() {
        detailsViewModel.getItemDetails(intent.extras!!.getInt("cityId")).observe(this, Observer {
            activityDetailsBinding.contentLayout.countryTv.text=it.sys.country
            activityDetailsBinding.contentLayout.descTv.text=it.weather.get(0).description
            activityDetailsBinding.contentLayout.minTv.text=it.main.temp_min.toString()
            activityDetailsBinding.contentLayout.maxTv.text=it.main.temp_max.toString()
            activityDetailsBinding.contentLayout.speedTv.text=it.wind.speed.toString()
            activityDetailsBinding.contentLayout.degTv.text=it.wind.deg.toString()
            activityDetailsBinding.contentLayout.clouds.text=it.clouds.clouds.toString()
            activityDetailsBinding.motionLayout.label.text=it.name
        })
    }
}