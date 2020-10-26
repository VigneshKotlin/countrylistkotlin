package com.interviewtask.app.webservice

import CountryListRes
import WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("all")
    fun countryList(): Observable<List<CountryListRes>>
    @GET("weather")
    fun weatherRes(@Query("q") city: String, @Query("lat") lat:String,
                   @Query("lon") lng: String, @Query("lang") language: String): Observable<WeatherResponse>
}