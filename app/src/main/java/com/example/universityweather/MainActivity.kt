package com.example.universityweather

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.universityweather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearchWeather.setOnClickListener {
            // TODO: connect to fetchWeather() from the networking module
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
