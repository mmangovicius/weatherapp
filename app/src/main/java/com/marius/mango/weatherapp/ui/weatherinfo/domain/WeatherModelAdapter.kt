package com.marius.mango.weatherapp.ui.weatherinfo.domain

import com.marius.mango.weatherapp.ui.weatherinfo.response.WeatherResponse
import java.util.*

fun WeatherResponse.toDomainModel() =
    Weather(
        description = weather.first().description,
        iconId = weather.first().iconId,
        temperature = temp.temp.toInt().toString(),
        temperatureMin = temp.tempMin,
        temperatureMax = temp.tempMax,
        date = date.toDate(),
        cityName = name
    )

private fun Long.toDate(): Date = Date(this * 1000)