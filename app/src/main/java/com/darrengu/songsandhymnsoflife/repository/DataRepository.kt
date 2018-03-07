package com.darrengu.songsandhymnsoflife.repository

import android.content.Context
import com.darrengu.songsandhymnsoflife.SongApplication
import com.darrengu.songsandhymnsoflife.model.Category
import com.darrengu.songsandhymnsoflife.model.Song
import com.darrengu.songsandhymnsoflife.model.Song_
import java.util.*

/**
 * Created by darren.gu on 3/4/18.
 */
class DataRepository {
    private val songBox = SongApplication.instance.boxStore.boxFor(Song::class.java)
    private val categoryBox = SongApplication.instance.boxStore.boxFor(Category::class.java)

    init {
        mockDataIfNecessary()
    }

    private fun mockDataIfNecessary() {
        val sharedPref = SongApplication.instance.getSharedPreferences("SHAREDPREF", Context.MODE_PRIVATE)
        val imported = sharedPref.getBoolean("IMPORTED", false)
        if (!imported) {
            val songs = mutableListOf<Song>()
            val categories = mutableListOf<Category>()
            for (index in 0..10) {
                val category = Category(categoryTitle = "category$index")
                categories.add(category)
            }
            categoryBox.put(categories)
            val random = Random()
            for (index in 0..575) {
                val song = Song(trackNumber = index + 1, title = "title$index")
                song.categories.add(categories[random.nextInt(11)])
                song.categories.add(categories[random.nextInt(11)])
                songs.add(song)
            }
            songBox.put(songs)
            sharedPref.edit().putBoolean("IMPORTED", true).apply()
        }
    }

    fun search(keyword: String): List<Song> = songBox.query().contains(Song_.title, keyword).build().find()

    fun getAllSongs(): List<Song> = songBox.all

    fun findSongByTrack(track: Long): Song? = songBox.query().equal(Song_.id, track).build().findFirst()
}