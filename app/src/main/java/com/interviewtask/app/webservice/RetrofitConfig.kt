package com.interviewtask.app.webservice

import android.security.identity.AccessControlProfileId
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {

    companion object{
        fun create(): APIService{
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://restcountries.eu/rest/v2/")
                .build()
            return retrofit.create(APIService::class.java)
        }

        fun createWithHeader(): APIService{
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor{chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                    .header("x-rapidapi-key", "37bafc7adfmsh6ebb0df43b231dfp1b69aejsna49b2d45e204")
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            val retrofit = Retrofit.Builder()
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://rapidapi.p.rapidapi.com/")
                .build()
            return retrofit.create(APIService::class.java)
        }
    }
}