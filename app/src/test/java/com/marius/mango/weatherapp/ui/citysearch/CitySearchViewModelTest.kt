package com.marius.mango.weatherapp.ui.citysearch

import com.google.common.truth.Truth.assertThat
import com.marius.mango.weatherapp.Event
import com.marius.mango.weatherapp.ui.weatherinfo.domain.Weather
import com.marius.mango.weatherapp.ui.citysearch.domain.ValidateCityNameUseCase
import com.marius.mango.weatherapp.ui.citysearch.domain.ValidateCityNameUseCase.Result
import com.marius.mango.weatherapp.ui.citysearch.domain.WeatherApiRepository
import com.marius.mango.weatherapp.ui.citysearch.domain.WeatherApiRepository.Result.Error
import com.marius.mango.weatherapp.ui.citysearch.domain.WeatherApiRepository.Result.Success
import com.marius.mango.weatherapp.utils.InstantExecutableExtension
import com.marius.mango.weatherapp.utils.LastValueObserver
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutableExtension::class)
class CitySearchViewModelTest {

    private lateinit var viewModel: CitySearchViewModel
    private val validateCityNameUseCase: ValidateCityNameUseCase = mockk()
    private val weatherApiRepository: WeatherApiRepository = mockk()

    private val last = LastValueObserver<CitySearchViewModel.State>()

    @BeforeEach
    fun setUp() {
        viewModel = CitySearchViewModel(validateCityNameUseCase, weatherApiRepository)
        viewModel.state.observeForever(last)
    }

    @Nested
    inner class OnShowWeather {

        @Nested
        inner class NameIsEmpty {

            @BeforeEach
            fun setUp() {
                every {
                    validateCityNameUseCase.invoke("")
                } returns Result.Empty
                viewModel.showCityWeather("")
            }

            @Test
            fun showsTextIsEmptyError() {
                assertThat(last.value?.showTextEmptyError).isNotNull()
            }
        }

        @ExperimentalCoroutinesApi
        @Nested
        inner class ApiReturnsError {

            @BeforeEach
            fun setUp() {
                every {
                    validateCityNameUseCase.invoke("vilnius")
                } returns Result.Success
                coEvery {
                    weatherApiRepository.getWeatherFor(any(), any())
                } returns Error(java.lang.Exception())
                viewModel.showCityWeather("vilnius")
            }

            @Test
            fun showsCityNotFoundError() = runBlockingTest {
                assertThat(last.value?.showCityNotFoundError).isNotNull()
            }

            @Test
            fun showsProgress() {
                assertThat(last.prevValues[1].showProgress).isTrue()
            }

            @Test
            fun hidesProgress() {
                assertThat(last.value?.showProgress).isFalse()
            }
        }

        @ExperimentalCoroutinesApi
        @Nested
        inner class ApiReturnsSuccess {

            private val weather = Weather(
                description = "description",
                iconId = "iconId",
                temperature = "temperature",
                temperatureMax = "22",
                temperatureMin = "19",
                date = Date(),
                cityName = "name"
            )

            @BeforeEach
            fun setUp() {
                every {
                    validateCityNameUseCase.invoke("vilnius")
                } returns Result.Success
                coEvery {
                    weatherApiRepository.getWeatherFor(any(), any())
                } returns Success(weather)
                viewModel.showCityWeather("vilnius")
            }

            @Test
            fun navigatesToWeatherInfo() = runBlockingTest {
                assertThat(last.value?.navigateToWeatherInfo).isEqualTo(Event(weather))
            }

            @Test
            fun showsProgress() {
                assertThat(last.prevValues[1].showProgress).isTrue()
            }

            @Test
            fun hidesProgress() {
                assertThat(last.value?.showProgress).isFalse()
            }
        }
    }
}