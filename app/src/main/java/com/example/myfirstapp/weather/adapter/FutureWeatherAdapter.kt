package com.example.myfirstapp.weather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.R
import com.example.myfirstapp.weather.info.DayForecast

class FutureWeatherAdapter(
  private val weatherData: List<DayForecast>,
) : RecyclerView.Adapter<FutureWeatherAdapter.ViewHolder>() {

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvDate: TextView = view.findViewById(R.id.tvDate)
    val tvWeek: TextView = view.findViewById(R.id.tvWeek)
    val tvDayWeather: TextView = view.findViewById(R.id.tvDayWeather)
    val tvDayTemp: TextView = view.findViewById(R.id.tvDayTemp)
    val tvNightTemp: TextView = view.findViewById(R.id.tvNightTemp)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_future_weather, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val weather = weatherData[position]
    holder.tvDate.text = weather.date
    holder.tvWeek.text = weather.week
    holder.tvDayWeather.text = weather.dayweather
    holder.tvDayTemp.text = weather.daytemp
    holder.tvNightTemp.text = weather.nighttemp
  }

  override fun getItemCount() = weatherData.size
}