package com.interviewtask.app.webservice

import com.interviewtask.app.countrylist.model.CountryListModel
import com.interviewtask.app.weather.model.WeatherResponseModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("all")
    fun countryList(): Observable<List<CountryListModel>>
    @GET("weather")
    fun weatherRes(@Query("q") city: String, @Query("lat") lat:String,
                   @Query("lon") lng: String, @Query("lang") language: String): Observable<WeatherResponseModel>
}