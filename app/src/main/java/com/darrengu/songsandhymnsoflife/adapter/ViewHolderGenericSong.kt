package com.darrengu.songsandhymnsoflife.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.darrengu.songsandhymnsoflife.model.Song
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.adapter_item_song.songTitle
import kotlinx.android.synthetic.main.adapter_item_song.songTrack

/**
 * Created by darren.gu on 3/5/18.
 */
class ViewHolderGenericSong(override val containerView: View, private val onClickSong: (Long) -> Unit)
    : RecyclerView.ViewHolder(containerView), LayoutContainer {
    var displayingSong: Song? = null

    init {
        containerView.setOnClickListener { displayingSong?.id?.let { onClickSong(it) }}
    }
    fun bind(song: Song) {
        displayingSong = song
        songTrack.text = song.trackNumber
        songTitle.text = song.title
    }
}