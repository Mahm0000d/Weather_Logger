package com.example.weatherlogger.ui.main

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.example.weatherlogger.R
import com.example.weatherlogger.data.Repository
import com.example.weatherlogger.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

private const val MY_PERMISSION_ACCESS_COARSE_LOCATION = 1
@AndroidEntryPoint
class MainActivity : AppCompatActivity(),View.OnClickListener,WeatherAdapter.Weather,CustomBottomSheetDialogFragment.BottomSheetInterface {
   private var adapter:WeatherAdapter?=null
    private var bottomSheetDialogFragment:CustomBottomSheetDialogFragment?=null
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
            if(p0!=null) {
                mainViewModel._latitude.value = p0.lastLocation.latitude
                mainViewModel._langtitude.value = p0!!.lastLocation.longitude
                mainViewModel.fetchWeatherData()
                startAlarmManager()
            }

        }
    }
    private val mainViewModel : MainViewModel by viewModels()
    lateinit var mainBinding: ActivityMainBinding
    private var mAnimatedLoader: AnimatedVectorDrawableCompat? = null
    private var mAnimationCallback: Animatable2Compat.AnimationCallback? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        mainBinding.error.btnRetry.setOnClickListener(this)
        mainBinding.saveBtn.setOnClickListener(this)
        requestLocationPermission()
        if (hasLocationPermission()) {
            bindLocationManager()
        }
        else
            requestLocationPermission()
        setUpuDialogObserver()
        setUpAdapter()
        setUpLoadingObserver()
        setUpErrorObserver()
        setUpWeatherDataObserver()
//        var bottomSheetBehavior=BottomSheetBehavior.from(mainBinding.bottomSheet.bottomSheetLayout)
//        bottomSheetBehavior.state=BottomSheetBehavior.STATE_SETTLING
      //  CustomBottomSheetDialogFragment().show(supportFragmentManager,"Dialog")
//        mainBinding.bottomSheet.refreshLayout.setOnClickListener(View.OnClickListener {
//            val outValue = TypedValue()
//            this.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
//            it.setBackgroundResource(outValue.resourceId)
//        })
       // mainBinding.bottomSheet.bottomSheetLayout.on
        // showLoader()
       // setUpWeatherDataObserver()
      // var r: Repository= Repository()
//        GlobalScope.launch {
//            Log.d("MainActivity", repository.getRemoteWeatherData("cairo").body()!!.name)
//        }
    }

    private fun setUpErrorObserver(){
        mainViewModel.errorText.observe(this, Observer {
            mainBinding.recyclerView.visibility= GONE
            mainBinding.error.layoutError.visibility=VISIBLE
            mainBinding.error.errorNetwork.text=it
        })
    }

    private fun setUpLoadingObserver() {
    mainViewModel.loadingSpinner.observe(this, Observer {
        if(it) {
            showLoader()
        }
        else
            hideLoader()
    })
    }

    private fun setUpWeatherDataObserver(){
     // mainViewModel.fetchWeatherData("cairo")
        mainViewModel.WeatherData.observe(this, Observer {
            if(!mainBinding.progressBar.layoutProgress.isVisible) {
                mainBinding.error.layoutError.visibility = GONE
                mainBinding.recyclerView.visibility = VISIBLE
                adapter!!.setData(it)
            }

            Log.d("MainActivity", it.size.toString())

        })
  }

    private fun showLoader() {
        mAnimatedLoader = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_progress)
        mainBinding.progressBar.ivProgress.setImageDrawable(mAnimatedLoader)
        val mainHandler = Handler(Looper.getMainLooper())
        if (mAnimatedLoader != null) {
            mAnimationCallback = object : Animatable2Compat.AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable?) {
                    mainHandler.post { mAnimatedLoader!!.start() }
                }
            }
            mAnimatedLoader!!.registerAnimationCallback(mAnimationCallback as Animatable2Compat.AnimationCallback)
            mAnimatedLoader!!.start()
        }
        mainBinding.progressBar.layoutProgress.setVisibility(View.VISIBLE)
    }

    private fun hideLoader() {
        if (mAnimatedLoader != null) {
            mAnimationCallback?.let { mAnimatedLoader!!.unregisterAnimationCallback(it) }
        }
        mainBinding.progressBar.layoutProgress.setVisibility(View.GONE)
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            MY_PERMISSION_ACCESS_COARSE_LOCATION
        )
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == MY_PERMISSION_ACCESS_COARSE_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                bindLocationManager()
            else
                finish()
        }
    }
    private fun bindLocationManager() {
         val fusedLocationProviderClient: FusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
        LifecycleBoundLocationManager(
            this,
            fusedLocationProviderClient, locationCallback
        )
    }

    override fun onClick(v: View?) {
        showLoader()
        when(v!!.id){
            R.id.btn_retry->{
                mainBinding.error.layoutError.visibility= GONE
                mainViewModel.fetchWeatherData()
                }
            R.id.save_btn->
                mainViewModel.serachByName(mainBinding.editText.text.toString())
        }
    }

    fun setUpAdapter(){
        adapter= WeatherAdapter(this)
        adapter!!.setWeatherListner(this)
        val layout:LinearLayoutManager= LinearLayoutManager(this)
        mainBinding.recyclerView.layoutManager=layout
        mainBinding.recyclerView.adapter=adapter
    }

    fun showErrorDialog(message:String){
        val builder1: AlertDialog.Builder = AlertDialog.Builder(this)
        builder1.setMessage(message)
        builder1.setCancelable(true)

        builder1.setNeutralButton(
            "Ok",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, id: Int) {
                    dialog.cancel()
                }
            })
        val alert11: AlertDialog = builder1.create()
        alert11.show()
    }

    fun setUpuDialogObserver(){
        mainViewModel.errorDialog.observe(this, Observer {
                showErrorDialog(it)
        })

    }

    override fun setLocation(lat: Double, lang: Double) {
        mainViewModel._latitude.value=lat
        mainViewModel._langtitude.value=lang
        Log.d("MainActivity",mainViewModel._latitude.value.toString())
    }

    override fun setId(id: Int) {
        mainViewModel._id.value=id

    }

    override fun openBottomSheet() {
        bottomSheetDialogFragment=CustomBottomSheetDialogFragment()
        bottomSheetDialogFragment!!.show(supportFragmentManager,"Dialog")
        bottomSheetDialogFragment!!.setOnBottomSheetListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter!!.setWeatherListner(null)
       // bottomSheetDialogFragment!!.setOnBottomSheetListener(null)

    }

    override fun refresh() {
        mainViewModel.fetchWeatherData()
    }

    override fun delete() {
        Log.d("MainActivity","okkkkkk")
        mainViewModel.deleteWeatherItem()
    }

    fun startAlarmManager(){
       // Log.d("MainActivity","alarm")

        val ctx: Context = applicationContext
        val now = Calendar.getInstance()

        val tmp = now.clone() as Calendar
        tmp.add(Calendar.MINUTE, 10)
        val nowPlus10Minutes = tmp
        val am: AlarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val interval = 1000 * 60 * 10.toLong() // 15 minutes in milliseconds

        val serviceIntent = Intent(ctx, RefreshService::class.java)

        val servicePendingIntent: PendingIntent = PendingIntent.getService(
            ctx,
            20,
            serviceIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        am.setRepeating(
            AlarmManager.RTC_WAKEUP,
            nowPlus10Minutes.getTimeInMillis(),
            interval,
            servicePendingIntent
        )
    }


    }
