package com.interviewtask.app.countrylist.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.interviewtask.app.weather.model.WeatherResponseModel
import com.interviewtask.app.webservice.RetrofitConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeVM : ViewModel() {
    val response = MutableLiveData<WeatherResponseModel>()

    fun weatherAPICall(lat: String, lng: String){
        val api = RetrofitConfig.createWithHeader()
        val observable = api.weatherRes("chennai", lat, lng, "en")
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
            { response -> getResponse(response) },
            { error -> displayError(error)}
        )
    }

    fun displayError(error: Throwable) {
        Log.i("VM", "Oops something went wrong", error)
    }

    fun getResponse(response: WeatherResponseModel) {
        Log.i("VM", "Message"+ Gson().toJson(response))
        this.response.value = response
    }
}