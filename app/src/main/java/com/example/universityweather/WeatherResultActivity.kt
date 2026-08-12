package com.example.universityweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.universityweather.databinding.ActivityWeatherResultBinding

class WeatherResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val city = intent.getStringExtra("CITY_NAME") ?: "Unknown"
        
        // Initializing with placeholders or passed data
        binding.tvCityName.text = city
        
        // Member 4 will fill these with real data
        showResults("29°C", "Cloudy", "78%", "12 km/h")
    }

    fun showResults(temp: String, condition: String, humidity: String, wind: String) {
        binding.tvTemperature.text = temp
        binding.tvCondition.text = condition
        binding.tvHumidity.text = humidity
        binding.tvWindSpeed.text = wind
    }
}