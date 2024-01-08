package com.example.myfirstapp.solodemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.R

class FrameLayoutActivity : AppCompatActivity() {
  private val foodList = arrayOf("苹果", "香蕉", "樱桃", "枣子", "鸡蛋",
    "鱼", "葡萄", "汉堡", "冰淇淋", "奇异果",
    "柠檬", "芒果", "橄榄", "橙子", "桃子",
    "梨", "菠萝", "草莓", "西瓜", "胡萝卜",
    "猕猴桃", "荔枝", "龙眼", "菠萝蜜", "杨桃",
    "石榴", "火龙果", "木瓜", "百香果", "榴莲")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_frame_layout)

    val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

    // 设置 Adapter 和 LayoutManager
    recyclerView.adapter = MyAdapter_food(foodList)
    recyclerView.layoutManager = LinearLayoutManager(this)
  }
}

