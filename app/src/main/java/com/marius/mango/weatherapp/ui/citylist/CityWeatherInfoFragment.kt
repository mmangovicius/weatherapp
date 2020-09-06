package com.marius.mango.weatherapp.ui.citylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import coil.api.load
import com.marius.mango.weatherapp.Navigator
import com.marius.mango.weatherapp.R
import com.marius.mango.weatherapp.ui.citylist.domain.Weather
import com.marius.mango.weatherapp.utils.arg
import kotlinx.android.synthetic.main.weather_info_card.*
import kotlinx.android.synthetic.main.weather_info_fragment.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CityWeatherInfoFragment : Fragment() {

    private val viewModel: CityWeatherInfoViewModel by viewModel()

    private val navigator: Navigator by inject()

    private val cityName: Weather by arg(ARG_CITY_NAME)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.weather_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            city_name_text.text = state.name
            description_text.text = state.description
            temp_text.text = state.temperature
            day_text.text = state.day
            month_text.text = state.month
            state.iconId?.getContentIfNotHandled()?.let { id ->
                weather_info_image.load(getString(R.string.weather_icon_uri, id))
            }
            state.goBack?.getContentIfNotHandled()?.let {
                navigator.navigateToCitySeach()
            }
        })
        back_button.setOnClickListener {
            viewModel.onBackClicked()
        }
        viewModel.showCityWeatherInfo(cityName)
    }

    companion object {
        private const val ARG_CITY_NAME = "weather"

        fun newInstance(weather: Weather) = CityWeatherInfoFragment().apply {
            arguments = bundleOf(ARG_CITY_NAME to weather)
        }
    }
}