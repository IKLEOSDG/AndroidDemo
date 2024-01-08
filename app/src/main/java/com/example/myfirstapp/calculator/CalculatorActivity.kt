package com.example.myfirstapp.calculator

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myfirstapp.R


// 1.了解Activity onCreate 回调
class CalculatorActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    setContentView(R.layout.activity_calculator)
      // 创建 Fragment 实例
      val calculatorFragment = CalculatorFragment()
      // 执行 Fragment 事务，添加 Fragment 到容器中
      supportFragmentManager.beginTransaction().add(R.id.fragment_container, calculatorFragment).commit()
  }
}

