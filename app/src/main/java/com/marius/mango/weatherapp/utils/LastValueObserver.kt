package com.marius.mango.weatherapp.utils

import androidx.lifecycle.Observer

class LastValueObserver<T> : Observer<T> {

    private val _prevValues = mutableListOf<T>()
    private var _value: T? = null

    val value: T?
        get() = _value

    val prevValues: List<T>
        get() = _prevValues

    override fun onChanged(value: T) {
        _prevValues.add(value)
        _value = value
    }
}
