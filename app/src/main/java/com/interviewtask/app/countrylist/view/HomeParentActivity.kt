package com.interviewtask.app.countrylist.view

import WeatherResponse
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.google.gson.Gson
import com.interviewtask.app.R
import com.interviewtask.app.countrylist.Communicator
import com.interviewtask.app.countrylist.viewmodel.HomeVM
import com.interviewtask.app.databinding.ActivityMainBinding
import com.interviewtask.app.weather.view.WeatherDialog


class HomeParentActivity : AppCompatActivity(), Communicator {

    private lateinit var binding: ActivityMainBinding
    private lateinit var client: FusedLocationProviderClient
    private var lat: String = ""
    private var lng: String = ""
    private lateinit var homeVM: HomeVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        init()
    }
    private fun init(){
        homeVM = ViewModelProviders.of(this).get(HomeVM::class.java)
        requestPermission()
        getCurrentLocation()
        loadFragment()
        val progressDialog = ProgressDialog(this, R.style.AppCompatAlertDialogStyle)

        binding.weather.setOnClickListener{
            callAPI(progressDialog)
        }
        homeVM.response.observe(this,
            Observer<WeatherResponse> {
                progressDialog.dismiss()
                Log.e("Activity", ">>"+Gson().toJson(it))
                var tempVal = it.main.temp - 273.15;
                WeatherDialog.showAlert(this, tempVal.toString(), it.weather[0].main, it.main.humidity.toString())
            })
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
    }

    private fun getCurrentLocation(){
        client = LocationServices.getFusedLocationProviderClient(this)
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return
        }
        client.lastLocation.addOnSuccessListener(
            OnSuccessListener<Location>{ location ->
            if(location != null){
                lat = location.latitude.toString()
                lng = location.longitude.toString()
            }
        })
    }

    private fun loadFragment(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout,
                CountryListFragment()
            )
            .commit()
    }

    override fun passDataCom(countryVal: String) {
        val bundle = Bundle()
        bundle.putString("country_data", countryVal)
        val transaction = supportFragmentManager.beginTransaction()
        val detailFragment = CountryDetailFragment()
        detailFragment.arguments = bundle

        transaction.add(R.id.frameLayout, detailFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun callAPI(progressDialog: ProgressDialog){
        progressDialog.setTitle(getString(R.string.app_name))
        progressDialog.setMessage("Loading, please wait")
        progressDialog.show()
        homeVM.weatherAPICall(lat, lng)
    }
}