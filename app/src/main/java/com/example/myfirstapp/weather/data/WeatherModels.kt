package com.example.myfirstapp.weather.data

import com.example.myfirstapp.weather.info.CurrentWeather
import com.example.myfirstapp.weather.info.DayForecast

enum class ItemType {
  CURRENT_WEATHER,
  FUTURE_WEATHER
}

data class WeatherItem(
  val type: ItemType,
  val currentWeather: CurrentWeather? = null,
  val dayForecast: DayForecast? = null
)

