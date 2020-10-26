package com.interviewtask.app.countrylist.viewmodel

import CountryListRes
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.interviewtask.app.webservice.RetrofitConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CountryListVM : ViewModel() {
    val response = MutableLiveData<List<CountryListRes>>()
    fun countryListAPICall(){
        val api = RetrofitConfig.create()
        val observable = api.countryList()
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
            { response -> getResponse(response) },
            { error -> displayError(error)}
        )
    }

    fun displayError(error: Throwable) {
        Log.i("VM", "Oops something went wrong", error)
    }

    fun getResponse(response: List<CountryListRes>) {
        Log.i("VM", "Message"+ Gson().toJson(response))
        this.response.value = response
    }
}