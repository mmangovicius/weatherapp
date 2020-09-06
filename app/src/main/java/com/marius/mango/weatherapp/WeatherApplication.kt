package com.marius.mango.weatherapp

import android.app.Application
import com.marius.mango.weatherapp.di.navigatorModule
import com.marius.mango.weatherapp.di.weatherApiModule
import com.marius.mango.weatherapp.ui.citylist.di.cityInfoModule
import com.marius.mango.weatherapp.ui.citysearch.di.citySearchModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(org.koin.core.logger.Level.DEBUG)
            androidContext(this@WeatherApplication)
            modules(
                listOf(
                    weatherApiModule,
                    navigatorModule,
                    cityInfoModule,
                    citySearchModule
                )
            )
        }
    }
}