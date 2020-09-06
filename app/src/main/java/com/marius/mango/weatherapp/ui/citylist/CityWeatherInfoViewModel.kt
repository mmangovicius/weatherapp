package com.marius.mango.weatherapp.ui.citylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.marius.mango.weatherapp.Event
import com.marius.mango.weatherapp.SafeLiveData
import com.marius.mango.weatherapp.SimpleEvent
import com.marius.mango.weatherapp.ui.citylist.domain.Weather
import com.marius.mango.weatherapp.utils.formatDayString
import com.marius.mango.weatherapp.utils.formatMonthString

class CityWeatherInfoViewModel() : ViewModel() {

    private val _state = SafeLiveData(State())
    val state: LiveData<State> get() = _state

    fun showCityWeatherInfo(weather: Weather) {
        _state.value = _state.value.copy(
            name = weather.cityName,
            description = weather.description.capitalize(),
            iconId = Event(weather.iconId),
            temperature = weather.temperature,
            minTemperature = weather.temperatureMin,
            maxTemperature = weather.temperatureMax,
            day = weather.date.formatDayString(),
            month = weather.date.formatMonthString()

        )
    }

    fun onBackClicked() {
        _state.value = _state.value.copy(goBack = SimpleEvent())
    }

    data class State(
        val name: String = "",
        val description: String = "",
        val iconId: Event<String>? = null,
        val temperature: String = "",
        val minTemperature: String = "",
        val maxTemperature: String = "",
        val day: String = "",
        val month: String = "",
        val goBack: SimpleEvent? = null
    )
}