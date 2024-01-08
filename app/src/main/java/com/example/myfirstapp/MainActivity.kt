package com.example.myfirstapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
  @SuppressLint("MissingInflatedId")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val demoButton = findViewById<Button>(R.id.button_demo)
    demoButton.setOnClickListener {
      // change color
//        demoButton.setBackgroundColor(Color.GRAY)
      // jump to MenuActivity
      val intent = Intent(this, MenuActivity::class.java)
      startActivity(intent)
    }
  }
}
