package com.marius.mango.weatherapp.ui.citysearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.marius.mango.weatherapp.Navigator
import com.marius.mango.weatherapp.R
import kotlinx.android.synthetic.main.city_search_fragment.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CitySearchFragment : Fragment() {

    private val viewModel: CitySearchViewModel by viewModel()
    private val navigator: Navigator by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.city_search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_button.setOnClickListener {
            viewModel.showCityWeather(city_edit_text.text.toString())
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            progress_bar.isVisible = state.showProgress
            state.navigateToWeatherInfo?.getContentIfNotHandled()?.let { weather ->
                navigator.navigateToWeatherInfo(weather)
            }
            state.showTextEmptyError?.getContentIfNotHandled()?.let {
                Toast.makeText(
                    context, getString(R.string.text_is_empty_toast), Toast.LENGTH_SHORT
                ).show()
            }
            state.showCityNotFoundError?.getContentIfNotHandled()?.let {
                Toast.makeText(
                    context, getString(R.string.city_not_found_toast), Toast.LENGTH_SHORT
                ).show()
                city_edit_text.text.clear()
            }
        })
    }

    companion object {
        fun newInstance() = CitySearchFragment()
    }
}