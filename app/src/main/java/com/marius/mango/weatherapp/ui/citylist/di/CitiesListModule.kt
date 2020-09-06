package com.marius.mango.weatherapp.ui.citylist.di

import com.marius.mango.weatherapp.ui.citylist.CityWeatherInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cityInfoModule = module {
    viewModel {
        CityWeatherInfoViewModel()
    }
}