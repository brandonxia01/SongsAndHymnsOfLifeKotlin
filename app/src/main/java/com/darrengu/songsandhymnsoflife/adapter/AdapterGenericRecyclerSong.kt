package com.darrengu.songsandhymnsoflife.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darrengu.songsandhymnsoflife.R
import com.darrengu.songsandhymnsoflife.model.Song

/**
 * Created by darren.gu on 3/5/18.
 */
class AdapterGenericRecyclerSong(private val onClickSong: (Long) -> Unit) : RecyclerView.Adapter<ViewHolderGenericSong>() {
    var dataSet: MutableList<Song> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGenericSong {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_song, parent, false)
        return ViewHolderGenericSong(itemView, onClickSong)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolderGenericSong, position: Int) {
        holder.bind(dataSet[position])
    }
}