package com.marius.mango.weatherapp.ui.weatherinfo.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @Json(name = "weather")
    val weather: List<Weather>,
    @Json(name = "main")
    val temp: Temp,
    @Json(name = "dt")
    val date: Long,
    @Json(name = "name")
    val name: String
)

@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "description")
    val description: String,
    @Json(name = "icon")
    val iconId: String
)

@JsonClass(generateAdapter = true)
data class Temp(
    @Json(name = "temp")
    val temp: Double,
    @Json(name = "temp_min")
    val tempMin: String,
    @Json(name = "temp_max")
    val tempMax: String
)