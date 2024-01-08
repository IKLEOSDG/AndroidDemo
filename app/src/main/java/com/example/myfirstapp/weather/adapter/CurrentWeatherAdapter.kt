package com.example.myfirstapp.weather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.R
import com.example.myfirstapp.weather.info.CurrentWeather

class CurrentWeatherAdapter(
  private val weatherData: List<CurrentWeather>,
) : RecyclerView.Adapter<CurrentWeatherAdapter.ViewHolder>() {

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvWeather: TextView = view.findViewById(R.id.tvWeather)
    val tvTemperature: TextView = view.findViewById(R.id.tvTemperature)
    val tvWindDirection: TextView = view.findViewById(R.id.tvWindDirection)
    val tvReportTime: TextView = view.findViewById(R.id.tvReportTime)
    val imWeatherDes: ImageView = view.findViewById(R.id.imWeatherDes)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_current_weather, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val weather = weatherData[position]
    holder.tvWeather.text = weather.weather
    holder.tvTemperature.text = weather.temperature
    holder.tvWindDirection.text = weather.winddirection
    holder.tvReportTime.text = weather.reporttime
    updateWeatherIcon(holder.tvWeather.text.toString(), holder.imWeatherDes) // 根据描述更新图标

  }

  private fun updateWeatherIcon(weatherDescription: String, imageView: ImageView) {
    val iconResId = when {
      weatherDescription.contains("晴") -> R.drawable.ic_weather_sunny
      weatherDescription.contains("多云") || weatherDescription.contains("阴") -> R.drawable.ic_weather_cloudy
      weatherDescription.contains("风") -> R.drawable.ic_weather_windy
      weatherDescription.contains("霾") -> R.drawable.ic_weather_haze
      weatherDescription.contains("雨") -> R.drawable.ic_weather_rainy
      weatherDescription.contains("雪") || weatherDescription.contains("冻雨") -> R.drawable.ic_weather_snowy
      weatherDescription.contains("雨夹雪") || weatherDescription.contains("阵雨夹雪") -> R.drawable.ic_weather_sleet
      weatherDescription.contains("浮尘") || weatherDescription.contains("扬沙") -> R.drawable.ic_weather_dust
      weatherDescription.contains("雾") || weatherDescription.contains("浓雾") -> R.drawable.ic_weather_fog
      weatherDescription.contains("热") -> R.drawable.ic_weather_hot
      weatherDescription.contains("冷") -> R.drawable.ic_weather_cold
      else -> R.drawable.ic_weather_unknown
    }
    imageView.setImageResource(iconResId)
  }

  override fun getItemCount() = weatherData.size
}
