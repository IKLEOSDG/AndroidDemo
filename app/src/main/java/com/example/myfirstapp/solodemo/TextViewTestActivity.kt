package com.example.myfirstapp.solodemo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.example.myfirstapp.R

class TextViewTestActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_text_view_test)

  }
  override fun onCreateOptionsMenu(menu:Menu): Boolean {
    menuInflater.inflate(R.menu.menu_textviewer, menu)
    finish()
    return true
  }


  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_website -> {
        showClickableTextView()
        true
      }
      R.id.menu_phone -> {
        // 处理电话菜单项的点击事件
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun showClickableTextView() {
    val textView :TextView= findViewById(R.id.textView_clickable)
    textView.apply {
      text = "点击访问网站"
      paint.isUnderlineText = true // 使文本呈现下划线，看起来像是链接
      setOnClickListener {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"))
        startActivity(intent)
      }
    }
  }
}