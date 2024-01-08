package com.example.myfirstapp.weather.info

data class ForecastResponse(
  val forecasts: List<FutureWeather>
)

data class FutureWeather(
  val city: String,
  val adcode: String,
  val province: String,
  val reporttime: String,
  val casts: List<DayForecast>
)

data class DayForecast(
  val date: String,
  val week: String,
  val dayweather: String,
  val nightweather: String,
  val daytemp: String,
  val nighttemp: String,
  val daywind: String,
  val nightwind: String,
  val daypower: String,
  val nightpower: String
  // 其他字段可以根据需要添加
)
