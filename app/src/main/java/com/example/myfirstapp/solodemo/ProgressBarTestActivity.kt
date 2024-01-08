package com.example.myfirstapp.solodemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.example.myfirstapp.R

class ProgressBarTestActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_progress_bar_test)

    val progressBar: ProgressBar = findViewById(R.id.progressBar)
    val buttonIncrease: Button = findViewById(R.id.button_increase_progress)

    buttonIncrease.setOnClickListener {
      val currentProgress = progressBar.progress
      if (currentProgress < progressBar.max) {
        progressBar.progress = currentProgress + 5
      }

      if (progressBar.progress >= progressBar.max) {
        showCompletionDialog()
      }
    }
  }

  private fun showCompletionDialog() {
    AlertDialog.Builder(this)
      .setTitle("助力成功")
      .setMessage("已帮好友完成助力")
      .setPositiveButton("确定") { dialog, which ->
        finish() // 结束当前 Activity，返回菜单界面
      }
      .setCancelable(false)//返回键和外部区域都不能使得对话框消失
      .show()
  }
}

