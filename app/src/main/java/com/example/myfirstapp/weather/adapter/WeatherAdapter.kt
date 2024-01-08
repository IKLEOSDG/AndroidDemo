package com.example.myfirstapp.weather.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.R
import com.example.myfirstapp.weather.data.ItemType
import com.example.myfirstapp.weather.data.WeatherItem
import com.example.myfirstapp.weather.info.CurrentWeather
import com.example.myfirstapp.weather.info.DayForecast

class WeatherAdapter(var items: MutableList<WeatherItem>) : RecyclerView.Adapter<RecyclerView
  .ViewHolder>
  () {

  override fun getItemViewType(position: Int): Int {
    return when (items[position].type) {
      ItemType.CURRENT_WEATHER -> R.layout.item_current_weather
      ItemType.FUTURE_WEATHER -> R.layout.item_future_weather
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
    return when (viewType) {
      R.layout.item_current_weather -> CurrentWeatherViewHolder(view)
      R.layout.item_future_weather -> FutureWeatherViewHolder(view)
      else -> throw IllegalArgumentException("Invalid view type")
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//    getItemViewType(position)
    val item = items[position]
    when (holder) {
      is CurrentWeatherViewHolder -> item.currentWeather?.let { holder.bind(it) }
      is FutureWeatherViewHolder -> {
        holder
         item.dayForecast?.let { holder.bind(it) }
      }
    }
  }


  override fun getItemCount() = items.size

  class CurrentWeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvWeather: TextView = view.findViewById(R.id.tvWeather)
    private val tvTemperature: TextView = view.findViewById(R.id.tvTemperature)
    private val tvWindDirection: TextView = view.findViewById(R.id.tvWindDirection)
    private val tvReportTime: TextView = view.findViewById(R.id.tvReportTime)
    private val imWeatherDes: ImageView = view.findViewById(R.id.imWeatherDes)

    fun bind(weather: CurrentWeather) {
      tvWeather.text = weather.weather
      tvTemperature.text = weather.temperature
      tvWindDirection.text = weather.winddirection
      tvReportTime.text = weather.reporttime
      updateWeatherIcon(weather.weather, imWeatherDes)
    }
    private fun updateWeatherIcon(weatherDescription: String, imageView: ImageView) {
      val iconResId = when {
        weatherDescription.contains("晴") -> R.drawable.ic_weather_sunny
        weatherDescription.contains("多云") & weatherDescription.contains("阴") -> R.drawable
          .ic_weather_cloudy
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
  }

  class FutureWeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvDate: TextView = view.findViewById(R.id.tvDate)
    private val tvWeek: TextView = view.findViewById(R.id.tvWeek)
    private val tvDayWeather: TextView = view.findViewById(R.id.tvDayWeather)
    private val tvDayTemp: TextView = view.findViewById(R.id.tvDayTemp)
    private val tvNightTemp: TextView = view.findViewById(R.id.tvNightTemp)

    fun bind(forecast: DayForecast) {
      tvDate.text = forecast.date
      tvWeek.text = forecast.week
      tvDayWeather.text = forecast.dayweather
      tvDayTemp.text = forecast.daytemp
      tvNightTemp.text = forecast.nighttemp
    }
  }
}

