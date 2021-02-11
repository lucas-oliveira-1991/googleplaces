package com.scb.googleplace.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scb.googleplace.Utils.BuildUrl
import com.scb.placemaps.models.Location
import com.scb.placemaps.models.Place
import com.scb.placemaps.network.PlaceApiService
import com.scb.placemaps.network.PlaceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Url

class MainViewModel : ViewModel() {

    val _response = MutableLiveData<Place>()

    val response: LiveData<Place>
        get() = _response



    fun getPlaces(local : String, dist : String, type : String) {
        val buildUrl = BuildUrl(local,dist,type)
        PlaceApi.retrofitService.getPlaces(buildUrl.url)!!.enqueue(
            object: Callback<Place?> {

                override fun onFailure(call: Call<Place?>?, t: Throwable?) {
                    if (t != null) {
                    }
                }

                override fun onResponse(call: Call<Place?>?, response: Response<Place?>?) {
                    _response.value = response?.body() ?: null;
                }
        })
    }


}

