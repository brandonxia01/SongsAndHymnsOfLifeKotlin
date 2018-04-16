package com.darrengu.songsandhymnsoflife.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import com.darrengu.songsandhymnsoflife.R
import com.darrengu.songsandhymnsoflife.adapter.AdapterGenericRecyclerSong
import com.darrengu.songsandhymnsoflife.model.Song
import com.darrengu.songsandhymnsoflife.viewmodel.ViewModelMainActivity
import kotlinx.android.synthetic.main.fragment_songlist.*
import kotlinx.android.synthetic.main.fragment_songlist.view.*

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
        allSongsList.layoutManager = LinearLayoutManager(context)
        allSongsList.adapter = adapter
        viewModel.allSongs.observe(this, Observer { allSongs: List<Song>? ->
            run {
                adapter.dataSet.clear()
                allSongs?.let { adapter.dataSet = it.toMutableList() }
            }
        })
        viewModel.getAllSongs(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sort, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort -> {
                if (sortByNumber) {
                    item.setIcon(R.drawable.ic_sort_by_alpha_white_24dp)
                } else {
                    item.setIcon(R.drawable.ic_format_list_numbered_white_24dp)
                }
                sortByNumber = !sortByNumber
                viewModel.getAllSongs(sortByNumber)
            }
        }

        return true
    }
}