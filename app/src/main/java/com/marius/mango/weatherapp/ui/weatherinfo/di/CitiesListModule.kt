package com.marius.mango.weatherapp.ui.weatherinfo.di

import com.marius.mango.weatherapp.ui.weatherinfo.CityWeatherInfoViewModel
import com.marius.mango.weatherapp.ui.weatherinfo.domain.Weather
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cityInfoModule = module {
    viewModel { (weather: Weather) ->
        CityWeatherInfoViewModel(weather)
    }
}