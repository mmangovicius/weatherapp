package com.marius.mango.weatherapp.ui.citysearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marius.mango.weatherapp.Event
import com.marius.mango.weatherapp.SafeLiveData
import com.marius.mango.weatherapp.SimpleEvent
import com.marius.mango.weatherapp.ui.citylist.domain.Weather
import com.marius.mango.weatherapp.ui.citysearch.domain.ValidateCityNameUseCase
import com.marius.mango.weatherapp.ui.citysearch.domain.WeatherApiRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CitySearchViewModel(
    private val validateCityNameUseCase: ValidateCityNameUseCase,
    private val weatherApiRepository: WeatherApiRepository
) : ViewModel() {

    private val _state = SafeLiveData(State())
    val state: LiveData<State> get() = _state

    fun showCityWeather(cityName: String) {
        when (validateCityNameUseCase(cityName)) {
            is ValidateCityNameUseCase.Result.Success ->
                getWeatherInfoFor(cityName)
            is ValidateCityNameUseCase.Result.Empty ->
                _state.value = _state.value.copy(showTextEmptyError = SimpleEvent())
        }
    }

    private fun getWeatherInfoFor(cityName: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(showProgress = true)
            val result = withContext(viewModelScope.coroutineContext) {
                weatherApiRepository.getWeatherFor(cityName, viewModelScope)
            }
            when (result) {
                is WeatherApiRepository.Result.Success -> {
                    _state.value = _state.value.copy(
                        navigateToWeatherList = Event(result.weather)
                    )
                }
                is WeatherApiRepository.Result.Error ->
                    _state.value = _state.value.copy(showCityNotFoundError = SimpleEvent())
            }
            _state.value = _state.value.copy(showProgress = false)
        }
    }

    data class State(
        val navigateToWeatherList: Event<Weather>? = null,
        val showTextEmptyError: SimpleEvent? = null,
        val showCityNotFoundError: SimpleEvent? = null,
        val showProgress: Boolean = false
    )
}