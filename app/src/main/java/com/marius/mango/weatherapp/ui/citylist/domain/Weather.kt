package com.marius.mango.weatherapp.ui.citylist.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Weather(
    val description: String,
    val iconId: String,
    val temperature: String,
    val temperatureMin: String,
    val temperatureMax: String,
    val date: Date,
    val cityName: String
) : Parcelable