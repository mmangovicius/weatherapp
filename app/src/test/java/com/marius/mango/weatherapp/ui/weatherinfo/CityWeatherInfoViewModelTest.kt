package com.marius.mango.weatherapp.ui.weatherinfo

import com.google.common.truth.Truth.assertThat
import com.marius.mango.weatherapp.Event
import com.marius.mango.weatherapp.ui.weatherinfo.domain.Weather
import com.marius.mango.weatherapp.utils.InstantExecutableExtension
import com.marius.mango.weatherapp.utils.LastValueObserver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutableExtension::class)
class CityWeatherInfoViewModelTest {

    private lateinit var viewModel: CityWeatherInfoViewModel
    private val last = LastValueObserver<CityWeatherInfoViewModel.State>()

    private val name = "vilnius"
    private val description = "description"
    private val temperature = "20"
    private val iconId = "id"
    private val date = Date(0)

    @BeforeEach
    fun setUp() {
        viewModel = CityWeatherInfoViewModel(
            Weather(
                description = description,
                iconId = iconId,
                temperature = temperature,
                temperatureMax = "22",
                temperatureMin = "19",
                date = date,
                cityName = name
            )
        )
        viewModel.state.observeForever(last)
    }

    @Nested
    inner class OnShowCityWeatherInfo {

        @Test
        fun emitsName() {
            assertThat(last.value?.name).isEqualTo(name)
        }

        @Test
        fun emitsDescription() {
            assertThat(last.value?.description).isEqualTo(description.capitalize())
        }

        @Test
        fun emitsIconId() {
            assertThat(last.value?.iconId).isEqualTo(Event(iconId))
        }

        @Test
        fun emitsTemperature() {
            assertThat(last.value?.temperature).isEqualTo(temperature)
        }

        @Test
        fun emitsDay() {
            assertThat(last.value?.day).isEqualTo("Thu")
        }

        @Test
        fun emitsMonth() {
            assertThat(last.value?.month).isEqualTo("01")
        }
    }

    @Nested
    inner class OnBackClicked {

        @BeforeEach
        fun setUp() {
            viewModel.onBackClicked()
        }

        @Test
        fun navigatesBack() {
            assertThat(last.value?.goBack).isNotNull()
        }
    }
}