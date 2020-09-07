package com.marius.mango.weatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.marius.mango.weatherapp.ui.citysearch.CitySearchFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            navigateTo(CitySearchFragment.newInstance())
        }
        actionBar?.hide()
        navigator.state.observe(this, Observer {
            it.navigateTo?.getContentIfNotHandled()?.let { fragment ->
                navigateTo(fragment)
            }
        })
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
    }
}