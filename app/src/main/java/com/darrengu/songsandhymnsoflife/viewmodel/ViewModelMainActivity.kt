package com.darrengu.songsandhymnsoflife.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.darrengu.songsandhymnsoflife.model.Song
import com.darrengu.songsandhymnsoflife.repository.DataRepository

/**
 * Created by darren.gu on 3/4/18.
 */
class ViewModelMainActivity : ViewModel() {
    private val repository = DataRepository()

    val allSongs = MutableLiveData<List<Song>>()
    val songInSearch = MutableLiveData<List<Song>>()
    val songNumber = MutableLiveData<Song>()

    fun getAllSongs() {
        allSongs.value = repository.getAllSongs()
    }

    fun search(keyword: String) {
        songInSearch.value = repository.search(keyword)
    }

    fun findSongByTrack(trackNumber: Long) {
        songNumber.value = repository.findSongByTrack(trackNumber)
    }
}