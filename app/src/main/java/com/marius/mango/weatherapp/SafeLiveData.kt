package com.marius.mango.weatherapp

import androidx.lifecycle.MutableLiveData

class SafeLiveData<T>(initData: T) : MutableLiveData<T>() {

    init {
        value = initData
    }

    @Suppress("UnsafeCallOnNullableType")
    override fun getValue(): T = super.getValue()!!
}
