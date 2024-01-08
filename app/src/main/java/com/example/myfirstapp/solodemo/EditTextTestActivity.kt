package com.example.myfirstapp.solodemo

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.myfirstapp.R

class EditTextTestActivity : AppCompatActivity() {
  private lateinit var adapter: ArrayAdapter<String>
  private val chatMessages = ArrayList<String>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_edit_text_test)

    val editTextMessage:EditText= findViewById(R.id.editText_message)
    val buttonSend:Button = findViewById(R.id.button_send)
    val listViewChat:ListView = findViewById(R.id.listView_chat)

    adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, chatMessages)
    listViewChat.adapter = adapter

    buttonSend.setOnClickListener {
      val message = editTextMessage.text.toString()
      if (message.isNotEmpty()) {//检查非空
        chatMessages.add(message)
        adapter.notifyDataSetChanged()//发送
        editTextMessage.text.clear()//清除文本输入新信
      }
    }
    val buttonClear :Button= findViewById(R.id.button_clear)
    buttonClear.setOnClickListener {
      chatMessages.clear() // 清空消息列表
      adapter.notifyDataSetChanged()
    }
  }
}
