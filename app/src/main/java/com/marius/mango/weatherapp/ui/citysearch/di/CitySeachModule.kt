package com.marius.mango.weatherapp.ui.citysearch.di

import com.marius.mango.weatherapp.ui.citysearch.CitySearchViewModel
import com.marius.mango.weatherapp.ui.citysearch.domain.ValidateCityNameUseCase
import com.marius.mango.weatherapp.ui.citysearch.domain.WeatherApiRepository
import com.marius.mango.weatherapp.ui.citysearch.domain.WeatherRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val citySearchModule = module {
    factory {
        ValidateCityNameUseCase()
    }
    viewModel {
        CitySearchViewModel(get(), get())
    }
    factory {
        WeatherRepository(get()) as WeatherApiRepository
    }
}