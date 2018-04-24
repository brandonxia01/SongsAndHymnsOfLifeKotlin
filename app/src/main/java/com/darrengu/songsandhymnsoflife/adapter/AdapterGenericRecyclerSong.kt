package com.darrengu.songsandhymnsoflife.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.darrengu.songsandhymnsoflife.R
import com.darrengu.songsandhymnsoflife.model.Song
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView

/**
 * Created by darren.gu on 3/5/18.
 */
class AdapterGenericRecyclerSong(private val onClickSong: (Long) -> Unit)
    : ListAdapter<Song, ViewHolderGenericSong>(SongDiffCallback()), FastScrollRecyclerView.SectionedAdapter {
    override fun getSectionName(position: Int): String {
        return getItem(position).subIndex?.first().toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGenericSong {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_song, parent, false)
        return ViewHolderGenericSong(itemView, onClickSong)
    }

    override fun onBindViewHolder(holder: ViewHolderGenericSong, position: Int) {
        holder.bind(getItem(position))
    }
}

class SongDiffCallback : DiffUtil.ItemCallback<Song>() {
    override fun areItemsTheSame(oldItem: Song?, newItem: Song?): Boolean {
        return oldItem?.songId == newItem?.songId
    }

    override fun areContentsTheSame(oldItem: Song?, newItem: Song?): Boolean {
        return oldItem?.equals(newItem) ?: false
    }
}