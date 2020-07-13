package com.darrengu.songsandhymnsoflife.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.darrengu.songsandhymnsoflife.R
import com.darrengu.songsandhymnsoflife.adapter.AdapterGenericRecyclerSong
import com.darrengu.songsandhymnsoflife.model.Song
import kotlinx.android.synthetic.main.fragment_songlist.allSongsList
import kotlinx.android.synthetic.main.fragment_songlist.view.toolbarList

/**
 * Created by darren.gu on 3/4/18.
 */
class FragSongList : BaseFragmentMainActivity() {
    var sortByNumber = true

    companion object {
        val TAG = FragSongList::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_songlist, container, false)
        (activity as AppCompatActivity).setSupportActionBar(view.toolbarList)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = AdapterGenericRecyclerSong(startScoreActivity)
        allSongsList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        allSongsList.adapter = adapter
        viewModel.allSongs.observe(viewLifecycleOwner, Observer { allSongs: List<Song>? ->
            run {
                adapter.dataSet.clear()
                allSongs?.let { adapter.dataSet = it.toMutableList() }
            }
        })
        viewModel.getAllSongs(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sort, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (sortByNumber) {
            item.setIcon(R.drawable.ic_sort_by_alpha_white_24dp)
        } else {
            item.setIcon(R.drawable.ic_format_list_numbered_white_24dp)
        }
        sortByNumber = !sortByNumber
        viewModel.getAllSongs(sortByNumber)
        return true
    }
}