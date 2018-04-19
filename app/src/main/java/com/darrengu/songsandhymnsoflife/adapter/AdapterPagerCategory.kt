package com.darrengu.songsandhymnsoflife.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darrengu.songsandhymnsoflife.R
import com.darrengu.songsandhymnsoflife.model.Category
import com.darrengu.songsandhymnsoflife.model.CategoryWithSongs
import com.darrengu.songsandhymnsoflife.model.Song
import java.util.*

class AdapterPagerCategory(var dataset: List<Any>, private val onClickListener: (Long) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val SONG = 0
        const val CATEGORY = 1
    }

    override fun getItemCount(): Int = dataset.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            SONG -> {
                val view = inflater.inflate(R.layout.adapter_item_song, parent, false)
                ViewHolderGenericSong(view, onClickListener)
            }
            else -> {
                val view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
                ViewHolderCategory(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataset[position] is Song) SONG else CATEGORY
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            SONG -> {
                val viewHolder = holder as ViewHolderGenericSong
                viewHolder.bind(dataset[position] as Song)
            }
            else -> {
                val viewHolder = holder as ViewHolderCategory
                viewHolder.bind(dataset[position] as Category)
            }
        }
    }


}
