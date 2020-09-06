package com.marius.mango.weatherapp

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.marius.mango.weatherapp.ui.citylist.CityWeatherInfoFragment
import com.marius.mango.weatherapp.ui.citylist.domain.Weather
import com.marius.mango.weatherapp.ui.citysearch.CitySearchFragment

class Navigator {

    private val _state = SafeLiveData(State())
    val state: LiveData<State> get() = _state

    fun navigateToCitySeach() {
        _state.value = _state.value.copy(
            navigateTo = Event(CitySearchFragment.newInstance())
        )
    }

    fun navigateToWeatherInfo(weather: Weather) {
        _state.value = _state.value.copy(
            navigateTo = Event(CityWeatherInfoFragment.newInstance(weather))
        )
    }

    data class State(val navigateTo: Event<Fragment>? = null)
}