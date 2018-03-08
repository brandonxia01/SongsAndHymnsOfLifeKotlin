package com.darrengu.songsandhymnsoflife.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import com.darrengu.songsandhymnsoflife.model.Song
import com.darrengu.songsandhymnsoflife.repository.DataRepository

/**
 * Created by darren.gu on 3/7/18.
 */
class ViewModelScoreActivity : ViewModel() {
    private val repository = DataRepository()
    val song: MutableLiveData<Song> = MutableLiveData()

    companion object {
        fun newInstance(activity: FragmentActivity) = ViewModelProviders.of(activity)[ViewModelScoreActivity::class.java]
    }

    fun findSongById(songId: Long) {
        song.value = repository.findSongById(songId)
    }
}