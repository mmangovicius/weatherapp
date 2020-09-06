package com.marius.mango.weatherapp.ui.citysearch.domain

import com.marius.mango.weatherapp.Constants.API_KEY
import com.marius.mango.weatherapp.api.Result
import com.marius.mango.weatherapp.api.WeatherApi
import com.marius.mango.weatherapp.api.apiCall
import com.marius.mango.weatherapp.ui.citylist.domain.toDomainModel
import com.marius.mango.weatherapp.ui.citylist.response.WeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

private const val UNITS = "metric"

class WeatherRepository(private val weatherApi: WeatherApi) :
    WeatherApiRepository {

    override suspend fun getWeatherFor(
        city: String,
        scope: CoroutineScope
    ): WeatherApiRepository.Result =
        withContext(scope.coroutineContext) {
            handleResult(apiCall(weatherApi) {
                cityWeather(city, UNITS, API_KEY)
            })
        }

    private fun handleResult(
        result: Result<WeatherResponse>
    ): WeatherApiRepository.Result = when (result) {
        is Result.Success ->
            WeatherApiRepository.Result.Success(result.data.toDomainModel())
        is Result.Error ->
            WeatherApiRepository.Result.Error(result.exception)
    }
}