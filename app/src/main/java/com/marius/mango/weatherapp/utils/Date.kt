package com.marius.mango.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatDayString(): String = SimpleDateFormat("E", Locale.getDefault())
    .format(this)

fun Date.formatMonthString(): String = SimpleDateFormat("MM", Locale.getDefault())
    .format(this).toUpperCase(Locale.getDefault())