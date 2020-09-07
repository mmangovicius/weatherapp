package com.marius.mango.weatherapp.di

import com.marius.mango.weatherapp.Navigator
import org.koin.dsl.module

val navigatorModule = module {
    single {
        Navigator()
    }
}
