package com.example.myfirstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import java.io.IOException

class WebActivity2 : AppCompatActivity() {

  private lateinit var textView: TextView
  private lateinit var requestButton: Button
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_web2)

    textView = findViewById(R.id.textView)
    requestButton = findViewById(R.id.button_request)

    requestButton.setOnClickListener {
      fetchWebPage()
    }
  }

  private fun fetchWebPage() {
    val client = OkHttpClient()
    val request = Request.Builder()
      .url("https://www.baidu.com")
      .build()

    client.newCall(request).enqueue(object : okhttp3.Callback {

      override fun onFailure(call: okhttp3.Call, e: IOException) {
        e.printStackTrace()
        Log.e("WebActivity2", "Network request failed: ${e.message}")
      }

      override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
        if (response.isSuccessful) {
          val htmlString = response.body?.string() ?: ""
          Log.d("WebActivity2", "Network request successful")
          // 解析 HTML
          val doc = Jsoup.parse(htmlString)
          val pageTitle = doc.title() // 获取页面标题

          //更新 TextView
          runOnUiThread {
            textView.text = pageTitle
          }
        }else{
          Log.e("WebActivity2", "Network request failed with code: ${response.code}")
        }
      }
    })
  }
}