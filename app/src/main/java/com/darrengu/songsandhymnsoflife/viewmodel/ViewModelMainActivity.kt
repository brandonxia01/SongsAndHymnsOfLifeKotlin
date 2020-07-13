package com.darrengu.songsandhymnsoflife.viewmodel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.darrengu.songsandhymnsoflife.model.Song
import com.darrengu.songsandhymnsoflife.repository.DataRepository

/**
 * Created by darren.gu on 3/4/18.
 */
class ViewModelMainActivity : ViewModel() {
    private val repository = DataRepository()

    val allSongs = MutableLiveData<List<Song>>()
    val songInSearch = MutableLiveData<List<Song>>()
    val songNumber = MutableLiveData<List<Song>>()

    companion object {
        fun newInstance(activity: FragmentActivity) = ViewModelProviders.of(activity)[ViewModelMainActivity::class.java]
    }

    fun getAllSongs(sortByNumber: Boolean) {
        allSongs.value = repository.getAllSongs(sortByNumber)
    }

    fun search(keyword: String) {
        songInSearch.value = repository.search(keyword)
    }

    fun findSongByTrack(trackNumber: String) {
        songNumber.value = repository.findSongByTrack(trackNumber)
    }
}