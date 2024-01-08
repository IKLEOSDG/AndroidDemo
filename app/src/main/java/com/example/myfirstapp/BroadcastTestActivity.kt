package com.example.myfirstapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class BroadcastTestActivity : AppCompatActivity() {
  lateinit var timeChangeReceiver: BroadcastReceiver
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_broadcast_test) // Make sure the layout name is correct
    timeChangeReceiver = TimeChangeReceiver()
    val intentFilter = IntentFilter()
    intentFilter.addAction("android.intent.action.TIME_TICK")
    registerReceiver(timeChangeReceiver, intentFilter)
  }

  override fun onDestroy() {
    super.onDestroy()
    unregisterReceiver(timeChangeReceiver)
  }

  inner class TimeChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
      Toast.makeText(context, "Time has changed", Toast.LENGTH_SHORT).show()
    }
  }
}