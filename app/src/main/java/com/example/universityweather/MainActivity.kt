package com.example.universityweather

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.universityweather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val repository = WeatherRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearchWeather.setOnClickListener {
            val city = binding.etCityName.text.toString()
            showLoading()

            repository.fetchWeather(
                city = city,
                onSuccess = { response ->
                    showResults(
                        city = response.name,
                        temp = "${response.main.temp}°C",
                        condition = response.weather[0].main,
                        humidity = "Humidity: ${response.main.humidity}%",
                        wind = "Wind Speed: ${response.wind.speed} km/h"
                    )
                },
                onError = { errorType, _ ->
                    val errorMessage = when (errorType) {
                        ErrorType.EMPTY_INPUT -> "Please enter a city name."
                        ErrorType.INVALID_CITY -> "City not found. Please check the spelling and try again."
                        ErrorType.NETWORK_ERROR -> "Can't connect. Please check your internet connection."
                        ErrorType.API_ERROR -> "Something went wrong. Please try again later."
                    }
                    showError(errorMessage)
                }
            )
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.tvErrorMessage.visibility = View.GONE

        // Hide result views
        setResultsVisibility(View.GONE)
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.tvErrorMessage.text = message
        binding.tvErrorMessage.visibility = View.VISIBLE

        // Hide result views
        setResultsVisibility(View.GONE)
    }

    private fun showResults(city: String, temp: String, condition: String, humidity: String, wind: String) {
        binding.progressBar.visibility = View.GONE
        binding.tvErrorMessage.visibility = View.GONE

        binding.tvCityName.text = city
        binding.tvTemperature.text = temp
        binding.tvCondition.text = condition
        binding.tvHumidity.text = humidity
        binding.tvWindSpeed.text = wind

        // Show result views
        setResultsVisibility(View.VISIBLE)
    }

    private fun setResultsVisibility(visibility: Int) {
        binding.tvCityName.visibility = visibility
        binding.tvTemperature.visibility = visibility
        binding.llCondition.visibility = visibility
        binding.clInfoCards.visibility = visibility
    }
}
