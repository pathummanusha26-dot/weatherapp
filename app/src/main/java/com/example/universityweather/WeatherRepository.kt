package com.example.universityweather

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class ErrorType { EMPTY_INPUT, INVALID_CITY, NETWORK_ERROR, API_ERROR }

class WeatherRepository {

    private val apiService = RetrofitClient.instance
    private val apiKey = BuildConfig.WEATHER_API_KEY

    fun fetchWeather(
        city: String,
        onSuccess: (WeatherResponse) -> Unit,
        onError: (ErrorType, String) -> Unit
    ) {
        if (city.isBlank()) {
            onError(ErrorType.EMPTY_INPUT, "Please enter a city name")
            return
        }

        apiService.getCurrentWeather(city, apiKey).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    if (weatherResponse != null && weatherResponse.cod == 200) {
                        onSuccess(weatherResponse)
                    } else {
                        // Handle cases where cod might not be 200 even if successful (though Retrofit usually handles 4xx/5xx in onFailure or !isSuccessful)
                        onError(ErrorType.API_ERROR, "API Error: ${response.code()}")
                    }
                } else {
                    when (response.code()) {
                        404 -> onError(ErrorType.INVALID_CITY, "City not found")
                        else -> onError(ErrorType.API_ERROR, "API Error: ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                onError(ErrorType.NETWORK_ERROR, "Network Error: ${t.message}")
            }
        })
    }
}
