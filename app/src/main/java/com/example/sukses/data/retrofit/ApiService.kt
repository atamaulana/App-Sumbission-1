package com.example.sukses.data.retrofit

import com.example.sukses.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("events")
    fun getActiveEvents(
        @Query("active") status: Int = 1
    ): Call<EventResponse>

    @GET("events")
    fun getFinishedEvents(
        @Query("active") status: Int = 0
    ): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailEvent(@Path("id") Id: String): Call<EventResponse>
}


