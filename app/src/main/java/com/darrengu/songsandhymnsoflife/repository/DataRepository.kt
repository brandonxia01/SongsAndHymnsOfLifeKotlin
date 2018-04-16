package com.darrengu.songsandhymnsoflife.repository

import android.content.Context
import android.util.Log
import com.darrengu.songsandhymnsoflife.SongApplication
import com.darrengu.songsandhymnsoflife.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.util.*

/**
 * Created by darren.gu on 3/4/18.
 */
class DataRepository {
    private val dao = SongDatabase.INSTANCE.getAppDAO()

    init {
        launch {
            mockDataIfNecessary()
        }
    }

    private fun mockDataIfNecessary() {
        try {
            val sharedPref = SongApplication.instance.getSharedPreferences("SHAREDPREF", Context.MODE_PRIVATE)
            val imported = sharedPref.getBoolean("IMPORTED", false)
            if (!imported) {
                val songs = mutableListOf<Song>()
                val categories = mutableListOf<Category>()

                categories.add(Category(100, 0L, "subCategory1001L", 1L))
                categories.add(Category(200, 1L, "subCategory2001L", 1L))
                categories.add(Category(300, 2L, "subCategory3002L", 2L))
                categories.add(Category(400, 3L, "subCategory4003L", 2L))
                categories.add(Category(400, 4L, "subCategory4004L", 2L))
                categories.add(Category(500, 4L, "subCategory5004L", 2L))


                categories.add(Category(1L, categoryTitle = "MainTitle1", parentCategory = null))
                categories.add(Category(2L, categoryTitle = "MainTitle2", parentCategory = null))


                dao.batchInsertCategories(categories)

                for (index in 0..5) {
                    val song = Song(trackNumber = (index + 1).toString(), title = "title$index", songId = index.toLong())
                    val postfix = String.format("%04d", index)
                    song.urlScore = "http://shengmingshige.net/blog/wp-content/gallery/c-png/cu-$postfix.png"
                    songs.add(song)
                }
                dao.batchInsertSongs(songs)

                sharedPref.edit().putBoolean("IMPORTED", true).apply()
            }
        } catch (e: Throwable) {
            Log.e("error",e.message)
        }

    }

    suspend fun search(keyword: String): List<Song> = async { dao.search("%$keyword%") }.await()

    suspend fun getAllSongs(sortByNumber: Boolean): List<Song> {
        return async {
            if (sortByNumber) {
                dao.getAllByTrackNumber()
            } else {
                dao.getAllByAlphabeticalOrder()
            }
        }.await()
    }

    suspend fun findSongByTrack(track: String): List<Song> = async { dao.findSongByTrack("$track%")}.await()

    suspend fun findSongById(songId: Int): Song? = async { dao.findSongById(songId) }.await()

    suspend fun findMainCategories(): List<Category> = async { dao.findMainCategories() }.await()

    suspend fun findSongsInCategory(id: Long): List<SongWithCategories> = async { dao.findSongInCategory(id) }.await()
}