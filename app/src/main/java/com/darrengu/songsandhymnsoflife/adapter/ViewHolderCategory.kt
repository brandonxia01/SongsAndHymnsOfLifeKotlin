package com.darrengu.songsandhymnsoflife.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.darrengu.songsandhymnsoflife.R
import com.darrengu.songsandhymnsoflife.model.Category
import com.darrengu.songsandhymnsoflife.model.Song
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.adapter_item_song.*
import kotlinx.android.synthetic.main.adapter_item_song_search.*

class ViewHolderCategory(override val containerView: View)
    : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(title: String) {
        containerView.findViewById<TextView>(android.R.id.text1).text = title
    }
}