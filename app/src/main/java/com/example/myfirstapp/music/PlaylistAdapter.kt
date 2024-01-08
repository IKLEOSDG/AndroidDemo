package com.example.myfirstapp.music

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapp.R

class PlaylistAdapter(
  private val playlist: List<MusicPlayerActivity.Song>,
  private val onSongSelected: (MusicPlayerActivity.Song) -> Unit
) : RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textViewSongName: TextView = view.findViewById(R.id.playlistItem)
    // 其他视图元素
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val song = playlist[position]
    holder.textViewSongName.text = song.name
    // 设置其他视图元素

    holder.itemView.setOnClickListener {
      onSongSelected(song)
    }
  }

  override fun getItemCount() = playlist.size
}
