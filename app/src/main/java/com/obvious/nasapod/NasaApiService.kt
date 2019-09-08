package com.obvious.nasapod

import com.obvious.nasapod.models.NasaPhoto
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApiService{
    @GET("planetary/apod")
    fun getData(@Query("api_key") api_key: String, @Query("date") date: String) : Single<NasaPhoto>

    /**
     * Companion object to create the NasaApiService
     */
    companion object Factory {
        fun create(): NasaApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.nasa.gov/")
                .build()

            return retrofit.create(NasaApiService::class.java);
        }
    }
}