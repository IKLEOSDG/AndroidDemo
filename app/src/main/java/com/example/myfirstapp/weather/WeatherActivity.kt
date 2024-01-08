package com.example.myfirstapp.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import okhttp3.*
import java.io.IOException
import org.json.JSONObject
import com.example.myfirstapp.R
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.weather.adapter.WeatherAdapter
import com.example.myfirstapp.weather.data.ItemType
import com.example.myfirstapp.weather.data.WeatherItem
import com.example.myfirstapp.weather.info.*
import com.google.gson.Gson

class WeatherActivity : AppCompatActivity() {
  private lateinit var imgViewChangeCity: ImageView
  private lateinit var tvCurrentCity: TextView
  private lateinit var weatherRecyclerView: RecyclerView
  private lateinit var weatherAdapter: WeatherAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_weather)

    imgViewChangeCity = findViewById(R.id.imgViewChangeCity)
    tvCurrentCity = findViewById(R.id.tvCurrentCity)
    weatherRecyclerView = findViewById(R.id.rvWeather)
    weatherRecyclerView.layoutManager = LinearLayoutManager(this)
    weatherAdapter = WeatherAdapter(mutableListOf())
    weatherRecyclerView.adapter = weatherAdapter
    displayDefaultWeatherData()
    val cityMap = getCityMapFromJson()
    imgViewChangeCity.setOnClickListener {
      showCitySelectionDialog(this, cityMap)
    }
    showCitySelectionDialog(this, cityMap)
  }

  private fun showCitySelectionDialog(context: Context, cityMap: Map<String, String>) {
    val cityNames = cityMap.keys.toTypedArray()
    var selectedCity = ""

    val builder = AlertDialog.Builder(context)
    builder.setTitle("选择城市")
    builder.setItems(cityNames) { _, which ->
      selectedCity = cityNames[which]
      tvCurrentCity.text = selectedCity
      val cityCode = cityMap[selectedCity]

      // 清除旧数据
      weatherAdapter.items.clear()
      weatherAdapter.notifyDataSetChanged()

      // 获取新城市的数据
      cityCode?.let {
        fetchWeatherData(it)
      }
    }
    builder.setNegativeButton("取消", null)
    val dialog = builder.create()
    dialog.show()
  }


  private fun getCityMapFromJson(): Map<String, String> {
    val cityMap = mutableMapOf<String, String>()
    val inputStream = resources.openRawResource(R.raw.cities)
    val jsonString = inputStream.bufferedReader().use { it.readText() }
    val jsonObject = JSONObject(jsonString)
    val cityKeys = jsonObject.keys()
    while (cityKeys.hasNext()) {
      val key = cityKeys.next()
      val value = jsonObject.getString(key)
      cityMap[key] = value
    }
    return cityMap
  }

  private fun isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
  }

  private fun fetchWeatherData(cityCode: String) {
    if (!isNetworkAvailable()) {
      displayDefaultWeatherData()
      return
    }
//    fetchRealtimeWeatherData(cityCode)
//    fetchForecastWeatherData(cityCode)
    fetchWeatherInfo(cityCode, "base") // 获取实时天气数据
    fetchWeatherInfo(cityCode, "all")  // 获取预报天气数据
  }

  private fun fetchWeatherInfo(cityCode: String, extension: String) {
    val client = OkHttpClient()
    val url = "https://restapi.amap.com/v3/weather/weatherInfo?city=$cityCode&extensions=$extension&key=bcc33c512939a035cd44f7539d8a17cd"
    val request = Request.Builder().url(url).build()

    client.newCall(request).enqueue(object : Callback {
      override fun onFailure(call: Call, e: IOException) {
        e.printStackTrace()
      }

      override fun onResponse(call: Call, response: Response) {
        response.use {
          val responseData = response.body?.string()
          Log.d("WeatherAPI", "Response Data: $responseData")
          responseData?.let {
            if (extension == "base") {
              parseAndAddRealtimeWeatherData(it)
            } else {
              parseAndAddForecastWeatherData(it)
            }
          }
        }
      }
    })
  }
//  private fun fetchRealtimeWeatherData(cityCode: String) {
//    val client = OkHttpClient()
//    val url = "https://restapi.amap.com/v3/weather/weatherInfo?city=$cityCode&extensions=base&key=bcc33c512939a035cd44f7539d8a17cd"
//    val request = Request.Builder().url(url).build()
//
//    client.newCall(request).enqueue(object : Callback {
//      override fun onFailure(call: Call, e: IOException) {
//        e.printStackTrace()
//      }
//
//      override fun onResponse(call: Call, response: Response) {
//        response.use {
//          val responseData = response.body?.string()
//          Log.d("WeatherAPI", "Realtime Response Data: $responseData")
//          responseData?.let {
//            parseAndAddRealtimeWeatherData(it)
//          }
//        }
//      }
//    })
//  }
//
//  private fun fetchForecastWeatherData(cityCode: String) {
//    val client = OkHttpClient()
//    val url = "https://restapi.amap.com/v3/weather/weatherInfo?city=$cityCode&extensions=all&key=bcc33c512939a035cd44f7539d8a17cd"
//    val request = Request.Builder().url(url).build()
//
//    client.newCall(request).enqueue(object : Callback {
//      override fun onFailure(call: Call, e: IOException) {
//        e.printStackTrace()
//      }
//
//      override fun onResponse(call: Call, response: Response) {
//        response.use {
//          val responseData = response.body?.string()
//          Log.d("WeatherAPI", "Forecast Response Data: $responseData")
//          responseData?.let {
//            parseAndAddForecastWeatherData(it)
//          }
//        }
//      }
//    })
//  }

  private fun parseAndAddRealtimeWeatherData(jsonData: String) {
    try {
      val gson = Gson()
      val weatherResponse = gson.fromJson(jsonData, WeatherResponse::class.java)
      weatherResponse?.lives?.firstOrNull()?.let { currentWeather ->
        runOnUiThread {
          // 更新 RecyclerView
          weatherAdapter.items.add(0, WeatherItem(ItemType.CURRENT_WEATHER, currentWeather =
          currentWeather))
          weatherAdapter.notifyItemInserted(0)


          // 根据当前天气状况更新背景图像
          updateBackgroundBasedOnWeather(currentWeather.weather)
        }
      }
    } catch (e: Exception) {
      Log.e("WeatherAPI", "Error parsing realtime weather JSON", e)
    }
  }


  private fun parseAndAddForecastWeatherData(jsonData: String) {
    try {
      val gson = Gson()
      val forecastResponse = gson.fromJson(jsonData, ForecastResponse::class.java)
      forecastResponse?.forecasts?.firstOrNull()?.casts?.let { forecasts ->
        runOnUiThread {
          forecasts.forEach { forecast ->
            weatherAdapter.items.toMutableList().add(WeatherItem(ItemType.FUTURE_WEATHER, dayForecast = forecast))
          }
          weatherAdapter.items = weatherAdapter.items.toMutableList()
          weatherAdapter.notifyDataSetChanged()
        }
      }
    } catch (e: Exception) {
      Log.e("WeatherAPI", "Error parsing forecast weather JSON", e)
    }
  }

  private fun displayDefaultWeatherData() {
    val defaultWeatherItems = listOf(
      WeatherItem(ItemType.CURRENT_WEATHER, currentWeather = CurrentWeather("N/A", "无数据", "N/A", "——", "N/A", "N/A", "N/A")),
      WeatherItem(ItemType.FUTURE_WEATHER, dayForecast = DayForecast("———", "#", "无数据", "无数据", "——", "——", "N/A", "N/A", "N/A", "N/A"))
    )
    weatherAdapter.items.clear()
    weatherAdapter.items.addAll(defaultWeatherItems)
    weatherAdapter.notifyDataSetChanged()
  }

  private fun updateBackgroundBasedOnWeather(weatherCondition: String) {
    val rootView = findViewById<View>(R.id.weatherLayout)
    val backgroundResId = when {
      weatherCondition.contains("晴") -> R.drawable.bg_default
      weatherCondition.contains("多云") -> R.drawable.bg_cloudy
      weatherCondition.contains("雨") -> R.drawable.bg_rainy
      else -> R.drawable.bg_default
    }
    rootView.setBackgroundResource(backgroundResId)
  }
}
