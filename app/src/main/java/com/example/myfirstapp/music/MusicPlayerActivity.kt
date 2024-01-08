package com.example.myfirstapp.music
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.R

class MusicPlayerActivity : AppCompatActivity() {
//定义区域
  private val handler = Handler(Looper.getMainLooper())
  private lateinit var textViewSongInfo: TextView
  private lateinit var buttonPrevious: ImageButton
  private lateinit var buttonPlayPause: ImageButton
  private lateinit var buttonNext: ImageButton
  private lateinit var seekBarSpeed: SeekBar
  private var currentTrackIndex = 0//歌曲索引
  private var mediaPlayer: MediaPlayer? = null
  private var isPlaying = false
  private lateinit var recyclerViewPlaylist: RecyclerView
  private lateinit var playlistAdapter: PlaylistAdapter
  private val playlist = listOf(
    Song(R.raw.test, "Test Song"),
    Song(R.raw.test1, "Test Song 1")
  )
//定义区域
  //主方法
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_music_main)
    recyclerViewPlaylist = findViewById(R.id.recyclerViewPlaylist)
    recyclerViewPlaylist.layoutManager = LinearLayoutManager(this)
    playlistAdapter = PlaylistAdapter(playlist){
        song ->
      // 处理歌曲被选中
      playSelectedSong(song)
    }
    recyclerViewPlaylist.adapter = playlistAdapter
    // 初始化组件
    textViewSongInfo = findViewById(R.id.textViewSongInfo)
    buttonPrevious = findViewById(R.id.buttonPrevious)
    buttonPlayPause = findViewById(R.id.buttonPlayPause)
    buttonNext = findViewById(R.id.buttonNext)
    seekBarSpeed = findViewById(R.id.seekBarSpeed)

    // 设置播放暂停按钮的点击事件
//  buttonPlayPause.setOnClickListener {
//    if (isPlaying) {
//      startMusicService("PAUSE")
//      isPlaying = false
//    } else {
//      startMusicService("PLAY", R.raw.test) // 假设 R.raw.test_song 是您要播放的歌曲
//      isPlaying = true
//    }
//  }
  buttonPlayPause.setOnClickListener {
    if (isPlaying) {
      pauseMusic()
    } else {
      playMusic()
    }
  }


  // 设置前一曲按钮的点击事件
    buttonPrevious.setOnClickListener {
      if (currentTrackIndex > 0) {
        currentTrackIndex--
      } else {
        currentTrackIndex = playlist.size - 1
      }
      playSelectedSong(playlist[currentTrackIndex])
    }
//设置后一曲的点击事件
    buttonNext.setOnClickListener {
      if (currentTrackIndex < playlist.size - 1) {
        currentTrackIndex++
      } else {
        currentTrackIndex = 0
      }
      playSelectedSong(playlist[currentTrackIndex])
    }

    // 设置播放速度调节滑动条的监听器
    seekBarSpeed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser) {
          mediaPlayer?.let {
            val totalDuration = it.duration
            val newPosition = (totalDuration * progress) / 1000
            it.seekTo(newPosition)
          }
        }
      }

      override fun onStartTrackingTouch(seekBar: SeekBar?) { }

      override fun onStopTrackingTouch(seekBar: SeekBar?) { }
    })

  }
  //选择歌曲的逻辑
  private fun playSelectedSong(song: Song) {
    mediaPlayer?.release()
    mediaPlayer = MediaPlayer.create(this, song.resourceId)
    mediaPlayer?.apply {
      setOnCompletionListener {
        this@MusicPlayerActivity.isPlaying = false
        buttonPlayPause.setImageResource(android.R.drawable.ic_media_play)
        seekBarSpeed.progress = seekBarSpeed.max
        handler.removeCallbacks(updateSeekBar)
      }
      start()
    }
    isPlaying = true
    buttonPlayPause.setImageResource(android.R.drawable.ic_media_pause)
    textViewSongInfo.text = song.name
    currentTrackIndex = playlist.indexOf(song)
    seekBarSpeed.max = 1000
    seekBarSpeed.progress = 0
    handler.post(updateSeekBar)
  }
//更新进度条
  private val updateSeekBar = object : Runnable {
    override fun run() {
      mediaPlayer?.let {
        val currentPosition = it.currentPosition
        val totalDuration = it.duration
        val progress = (currentPosition.toDouble() / totalDuration.toDouble()) * 1000
        seekBarSpeed.progress = progress.toInt()
      }
      handler.postDelayed(this, 1000)
    }
  }
//音月播放逻辑
  private fun playMusic() {
    if (mediaPlayer == null) {
      mediaPlayer = MediaPlayer.create(this, R.raw.test)
      mediaPlayer?.setOnCompletionListener {
        // 当一首歌曲播放完毕时
        isPlaying = false
        buttonPlayPause.setImageResource(android.R.drawable.ic_media_play)
      }
    }
    mediaPlayer?.start()
    isPlaying = true
    buttonPlayPause.setImageResource(android.R.drawable.ic_media_pause)
    handler.post(updateSeekBar)
  }
  private fun pauseMusic() {
    mediaPlayer?.pause()
    isPlaying = false
    buttonPlayPause.setImageResource(android.R.drawable.ic_media_play)
    handler.removeCallbacks(updateSeekBar)
  }
  //歌曲数据
  data class Song(val resourceId: Int, val name: String)
  //启动服务跳转到service
  private fun startMusicService(action: String, resourceId: Int = 0) {
    val intent = Intent(this, MusicService::class.java)
    intent.putExtra("ACTION", action) // 使用 "ACTION" 作为键
    if (resourceId != 0) {
      intent.putExtra("RESOURCE_ID", resourceId)
    }
    startService(intent)
  }



}


