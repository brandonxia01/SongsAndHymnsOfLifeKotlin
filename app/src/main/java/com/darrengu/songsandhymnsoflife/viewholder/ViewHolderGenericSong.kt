package com.darrengu.songsandhymnsoflife.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.darrengu.songsandhymnsoflife.model.Song
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.adapter_item_song.*

/**
 * Created by darren.gu on 3/5/18.
 */
class ViewHolderGenericSong(override val containerView: View, private val onClickSong: (Long) -> Unit)
    : RecyclerView.ViewHolder(containerView), LayoutContainer {
    private var displayingSong: Song? = null

    init {
        containerView.setOnClickListener { displayingSong?.songId?.let { onClickSong(it) }}
    }
    fun bind(song: Song) {
        displayingSong = song
        songTrack.text = song.trackNumber.toString()
        songTitle.text = song.title
    }
}