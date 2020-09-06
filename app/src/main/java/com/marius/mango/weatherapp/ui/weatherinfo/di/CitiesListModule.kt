package com.marius.mango.weatherapp.ui.weatherinfo.di

import com.marius.mango.weatherapp.ui.weatherinfo.CityWeatherInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cityInfoModule = module {
    viewModel {
        CityWeatherInfoViewModel()
    }
}