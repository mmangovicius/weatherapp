package com.marius.mango.weatherapp.ui.citysearch.domain

import com.marius.mango.weatherapp.ui.citylist.domain.Weather
import kotlinx.coroutines.CoroutineScope

interface WeatherApiRepository {

    suspend fun getWeatherFor(city: String, scope: CoroutineScope): Result

    sealed class Result {

        data class Success(val weather: Weather) : Result()

        data class Error(val exception: Exception) : Result()
    }
}