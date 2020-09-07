package com.marius.mango.weatherapp.api

import android.util.Log
import com.marius.mango.weatherapp.Constants.TAG
import kotlinx.coroutines.Deferred
import retrofit2.HttpException

suspend fun <T> apiCall(
    api: WeatherApi,
    call: WeatherApi.() -> Deferred<T>
): Result<T> = try {
    val response = call(api).await()
    Result.Success(response)
} catch (e: Exception) {
    logNetworkProblem(e)
    Result.Error(e)
}

private fun logNetworkProblem(cause: Exception) {
    when (cause) {
        is HttpException ->
            Log.w(
                TAG,
                formatHttpErrorLogMessage(cause),
                NetworkCallException(cause)
            )
        else -> Log.w(
            TAG, "Network call error",
            NetworkCallException(cause)
        )
    }
}

private fun formatHttpErrorLogMessage(cause: HttpException) = """
    API Error. Response code [${cause.code()}]"""

private class NetworkCallException(cause: Throwable) : RuntimeException(cause)