package com.marius.mango.weatherapp.ui.citysearch.domain

class ValidateCityNameUseCase {

    operator fun invoke(cityName: String): Result =
        if (cityName.isEmpty()) Result.Empty else Result.Success

    sealed class Result {

        object Success : Result()

        object Empty : Result()
    }
}