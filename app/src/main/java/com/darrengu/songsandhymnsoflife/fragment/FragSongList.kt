package com.darrengu.songsandhymnsoflife.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darrengu.songsandhymnsoflife.R
import com.darrengu.songsandhymnsoflife.adapter.AdapterGenericRecyclerSong
import com.darrengu.songsandhymnsoflife.model.Song
import com.darrengu.songsandhymnsoflife.viewmodel.ViewModelMainActivity
import kotlinx.android.synthetic.main.fragment_songlist.*

/**
 * Created by darren.gu on 3/4/18.
 */
class FragSongList : BaseFragmentMainActivity() {
    companion object {
        val TAG = FragSongList::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_songlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = AdapterGenericRecyclerSong(startScoreActivity)
        allSongsList.layoutManager = LinearLayoutManager(context)
        allSongsList.adapter = adapter
        viewModel.allSongs.observe(this, Observer { allSongs: List<Song>? ->
            run {
                adapter.dataSet.clear()
                allSongs?.let { adapter.dataSet = it.toMutableList() }
            }
        })
        viewModel.getAllSongs()
    }
}