package com.darrengu.songsandhymnsoflife.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import com.darrengu.songsandhymnsoflife.model.Song
import com.darrengu.songsandhymnsoflife.repository.DataRepository
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

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
        launch(UI) {
            try {
                song.value = repository.findSongById(songId)
            } catch (e: Throwable) {
                println(e.toString())
            }
        }
    }
}