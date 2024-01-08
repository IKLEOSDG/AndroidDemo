package com.example.myfirstapp.music

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.myfirstapp.R

class MusicService : Service() {
  private var mediaPlayer: MediaPlayer? = null
  private val channelId = "music_service_channel"
  private var isPlaying = false

  override fun onCreate() {
    super.onCreate()
    createNotificationChannel()
  }
  override fun onBind(intent: Intent): IBinder? {
    return null
  }
  private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel(channelId, "Music Service Channel", NotificationManager.IMPORTANCE_DEFAULT)
      val manager = getSystemService(NotificationManager::class.java)
      manager?.createNotificationChannel(channel)
    }
  }

  private fun showNotification(playbackStatus: String) {
    val notificationIntent = Intent(this, MusicPlayerActivity::class.java)
    // 注意这里的 FLAG_IMMUTABLE 标志
    val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

    // 创建播放/暂停操作的 PendingIntent
    val playPauseIntent = Intent(this, MusicService::class.java).apply {
      putExtra("COMMAND", "PLAY_PAUSE")
    }
    // 同样，注意 FLAG_IMMUTABLE 标志
    val playPausePendingIntent = PendingIntent.getService(this, 0, playPauseIntent, PendingIntent.FLAG_IMMUTABLE)

    val notification = NotificationCompat.Builder(this, channelId)
      .setContentTitle("Music Player")
      .setContentText("Now Playing: $playbackStatus")
      .setSmallIcon(R.drawable.setting)
      .setContentIntent(pendingIntent)
      .addAction(R.drawable.no, "Play/Pause", playPausePendingIntent)
      .build()

    startForeground(1, notification)
  }


  override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
    val action = intent.getStringExtra("ACTION") // 使用相同的键 "ACTION"
    when (action) {
      "PLAY" -> {
        val resourceId = intent.getIntExtra("RESOURCE_ID", 0)
        if (resourceId != 0) {
          playMusic(resourceId)
        }
      }
      "PAUSE" -> {
        pauseMusic()
      }
      // 添加对 PLAY_PAUSE 的处理，如果需要
    }
    return START_STICKY
  }


  private fun playMusic(resourceId: Int) {
    mediaPlayer?.release()
    mediaPlayer = MediaPlayer.create(this, resourceId)
    mediaPlayer?.start()
    isPlaying = true
    // 更新通知

    showNotification("Playing Song")
  }

  private fun pauseMusic() {
    mediaPlayer?.pause()
    isPlaying = false
    // 更新通知
    showNotification("Paused")
  }



  // 其他必要方法
}
