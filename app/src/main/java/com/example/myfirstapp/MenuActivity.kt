package com.example.myfirstapp

import WebActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myfirstapp.calculator.CalculatorActivity
import com.example.myfirstapp.music.MusicPlayerActivity
import com.example.myfirstapp.solodemo.EditTextTestActivity
import com.example.myfirstapp.solodemo.FrameLayoutActivity
import com.example.myfirstapp.solodemo.ImageViewTestActivity
import com.example.myfirstapp.solodemo.ProgressBarTestActivity
import com.example.myfirstapp.solodemo.RelativeLayoutActivity
import com.example.myfirstapp.solodemo.TextViewTestActivity
import com.example.myfirstapp.weather.WeatherActivity

class MenuActivity : AppCompatActivity() {
  @SuppressLint("MissingInflatedId")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_menu)
//    val testButton1 = findViewById<Button>(R.id.textviewertest)
//    testButton1.setOnClickListener {
//
//      // change color
//      testButton1.setBackgroundColor(Color.GRAY)
//      val intent = Intent(this, TextViewTestActivity::class.java)
//      startActivity(intent)
//    }
    val testButton2 = findViewById<Button>(R.id.edittexttest)
    testButton2.setOnClickListener {
      // change color
      testButton2.setBackgroundColor(Color.GRAY)
      val intent = Intent(this, EditTextTestActivity::class.java)
      startActivity(intent)
    }
    val testButton3 = findViewById<Button>(R.id.imageviewtest)
    testButton3.setOnClickListener {
      // change color
      testButton3.setBackgroundColor(Color.GRAY)
      val intent = Intent(this, ImageViewTestActivity::class.java)
      startActivity(intent)
    }
    val testButton4 = findViewById<Button>(R.id.progresstest)
    testButton4.setOnClickListener {
      // change color
      testButton4.setBackgroundColor(Color.GRAY)
      val intent = Intent(this, ProgressBarTestActivity::class.java)
      startActivity(intent)
    }
    val testButton5 = findViewById<Button>(R.id.relativelayouttest)
    testButton5.setOnClickListener {
      // change color
      testButton5.setBackgroundColor(Color.GRAY)
      val intent = Intent(this, RelativeLayoutActivity::class.java)
      startActivity(intent)
    }
    val testButton6 = findViewById<Button>(R.id.recyclerViewtest)
    testButton6.setOnClickListener {
      // change color
      testButton6.setBackgroundColor(Color.RED)
      val intent = Intent(this, FrameLayoutActivity::class.java)
      startActivity(intent)
    }
    val testButton7 = findViewById<Button>(R.id.calculator)
    testButton7.setOnClickListener {
      // change color
      testButton7.setBackgroundColor(Color.RED)
      val intent = Intent(this, CalculatorActivity::class.java)
      startActivity(intent)
    }
    val testButton8 = findViewById<Button>(R.id.boardcasttest)
    testButton8.setOnClickListener {
      // change color
      testButton8.setBackgroundColor(Color.BLUE)
      val intent = Intent(this, BroadcastTestActivity::class.java)
      startActivity(intent)
    }
    val testButton9 = findViewById<Button>(R.id.musictest)
    testButton9.setOnClickListener {
      // change color
      testButton9.setBackgroundColor(Color.BLUE)
      val intent = Intent(this, WeatherActivity::class.java)
      startActivity(intent)
    }


  }
}