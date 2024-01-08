package com.example.myfirstapp.solodemo
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.myfirstapp.R
import java.util.*


class ImageViewTestActivity : AppCompatActivity() {
  private val defaultpic = R.drawable.setting
  private lateinit var imageView: ImageView//修改：推迟初始化，初始化了就是空的会报错
  private var timer: Timer? = null//特殊定义可空的计时器
  private val images = arrayOf(
    R.drawable.big,
    R.drawable.no,
    R.drawable.meat,
    R.drawable.pear,
    R.drawable.tomato
  )
  @SuppressLint("MissingInflatedId")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_image_view_test) // 确保布局文件名正确

    imageView = findViewById(R.id.imageView_random)
    val buttonStart:Button = findViewById(R.id.button_start)
    val buttonStop:Button = findViewById(R.id.button_stop)
    val buttonClear:Button = findViewById(R.id.button_clear)

    buttonStart.setOnClickListener { startLottery() }
    buttonStop.setOnClickListener { stopLottery() }
    buttonClear.setOnClickListener { clearImage() }
  }

  private fun startLottery() {
    timer?.cancel() // 如果有就关闭计时器
    timer = Timer().apply {
      schedule(object : TimerTask() {
        override fun run() {
          runOnUiThread {//开启线程
            val randomIndex = (images.indices).random()
            imageView.setImageResource(images[randomIndex])
          }
        }
      }, 0, 100) // 每100毫秒变换一次图片
    }
  }

  private fun stopLottery() {
    timer?.cancel()//close timer
  }

  private fun clearImage() {
    imageView.setImageResource(defaultpic) // remove
  }
}
