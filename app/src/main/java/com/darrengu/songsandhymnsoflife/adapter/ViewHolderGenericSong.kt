package com.darrengu.songsandhymnsoflife.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.darrengu.songsandhymnsoflife.model.Song
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.adapter_item_song.*

/**
 * Created by darren.gu on 3/5/18.
 */
class ViewHolderGenericSong(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(song: Song) {
        songTrack.text = song.trackNumber.toString()
        songTitle.text = song.title
    }
}