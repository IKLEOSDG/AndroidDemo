package com.example.myfirstapp.solodemo

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter_food(private val foodList: Array<String>) :
  RecyclerView.Adapter<MyAdapter_food.MyViewHolder>() {

  class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val textView = TextView(parent.context).apply {
      layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
      )
      textSize = 80f
      //padding = 16
    }
    return MyViewHolder(textView)
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    holder.textView.text = foodList[position]
  }

  override fun getItemCount() = foodList.size
}