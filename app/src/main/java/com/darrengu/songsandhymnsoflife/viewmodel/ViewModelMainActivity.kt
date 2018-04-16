package com.darrengu.songsandhymnsoflife.viewmodel

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.darrengu.songsandhymnsoflife.model.Category
import com.darrengu.songsandhymnsoflife.model.Song
import com.darrengu.songsandhymnsoflife.model.SongDatabase
import com.darrengu.songsandhymnsoflife.model.SongWithCategories
import com.darrengu.songsandhymnsoflife.repository.DataRepository
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * Created by darren.gu on 3/4/18.
 */
class ViewModelMainActivity : ViewModel() {
    private val repository = DataRepository()

    val allSongs = MutableLiveData<List<Song>>()
    val songInSearch = MutableLiveData<List<Song>>()
    val songNumber = MutableLiveData<List<Song>>()
    val mainCategories = MutableLiveData<List<Category>>()
    val categories: MutableMap<Long, List<SongWithCategories>> = HashMap()

    companion object {
        fun newInstance(activity: FragmentActivity) = ViewModelProviders.of(activity)[ViewModelMainActivity::class.java]
    }

    fun getAllSongs(sortByNumber: Boolean) {
        launch(UI) {
            try {
                allSongs.value = repository.getAllSongs(sortByNumber)
            } catch (e: Throwable) {
                Log.e("error",e.message)
            }
        }
    }

    fun search(keyword: String) {
        launch(UI) {
            songInSearch.value = repository.search(keyword)
        }
    }

    fun findSongByTrack(trackNumber: String) {
        launch(UI) {
            songNumber.value = repository.findSongByTrack(trackNumber)
        }
    }

    fun findMainCategories() {
        launch(UI) {
            mainCategories.value =  repository.findMainCategories()
        }
    }

    fun findSongsInCategory(categoryId: Long): List<SongWithCategories>? {
        return if (categories.contains(categoryId)) {
            categories[categoryId]
        } else {
            runBlocking {
                val songs = repository.findSongsInCategory(categoryId)
                categories[categoryId] = songs
                songs
            }
        }

    }

}