package com.marius.mango.weatherapp.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.marius.mango.weatherapp.BuildConfig
import com.marius.mango.weatherapp.Constants.WEATHER_API_URL
import com.marius.mango.weatherapp.api.WeatherApi
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val weatherApiModule = module {
    single {
        provideOkHttp()
    }
    single {
        provideMoshi()
    }
    single {
        provideRetrofitBuilder(get(), get())
    }
    single {
        get<Retrofit>().create(WeatherApi::class.java)
    }
}

private val interceptor: Interceptor
    get() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

private fun provideOkHttp() = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .build()

private fun provideMoshi() = Moshi.Builder()
    .build()


private fun provideRetrofitBuilder(okHttpClient: OkHttpClient, moshi: Moshi) = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(WEATHER_API_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()
