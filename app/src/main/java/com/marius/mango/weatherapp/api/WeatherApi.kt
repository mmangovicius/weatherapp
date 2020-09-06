package com.marius.mango.weatherapp.api

import com.marius.mango.weatherapp.ui.citylist.response.WeatherResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather")
    fun cityWeather(
        @Query("q")
        name: String,
        @Query("units")
        units: String,
        @Query("appid")
        apiKey: String
    ): Deferred<WeatherResponse>
}