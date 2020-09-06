package com.marius.mango.weatherapp.utils

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T : Any> Fragment.arg(key: String): ReadWriteProperty<Fragment, T> = FragmentArgDelegate(key)

private class FragmentArgDelegate<T : Any>(private val key: String) :
    ReadWriteProperty<Fragment, T> {

    override operator fun getValue(thisRef: Fragment, property: KProperty<*>): T =
        checkNotNull(thisRef.arguments?.get(key)) { "Mandatory argument $key is missing" } as T

    override operator fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        thisRef.putArg(key to value)
    }
}

private fun Fragment.putArg(arg: Pair<String, Any?>) {
    args().putAll(bundleOf(arg))
}

private fun Fragment.args() = arguments.takeIf { it != null } ?: Bundle().also { arguments = it }