package com.scb.placemaps.network

import com.scb.placemaps.models.Place
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url


private val BASE_URL =
    "https://maps.googleapis.com/maps/api/place/nearbysearch/"


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory .create())
    .baseUrl(BASE_URL)
    .build()


interface PlaceApiService  {
    @GET
    fun getPlaces(@Url url: String?): Call<Place?>?

}

object PlaceApi {
    val retrofitService : PlaceApiService by lazy { retrofit.create(PlaceApiService::class.java) }
}

