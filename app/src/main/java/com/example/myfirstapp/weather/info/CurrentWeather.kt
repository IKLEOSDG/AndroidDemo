package com.example.myfirstapp.weather.info
data class WeatherResponse(
  val lives: List<CurrentWeather>
)

data class CurrentWeather(
  val city: String,
  val weather: String,
  val temperature: String,
  val winddirection: String,
  val windpower: String,
  val humidity: String,
  val reporttime: String
)

