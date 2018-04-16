package com.darrengu.songsandhymnsoflife.model

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface SongDAO {
    @Query("SELECT * FROM song ORDER BY track_number")
    fun getAllByTrackNumber(): List<Song>

    @Query("SELECT * FROM song ORDER BY sub_index")
    fun getAllByAlphabeticalOrder(): List<Song>

    @Query("SELECT * FROM song WHERE title LIKE :keyword")
    fun search(keyword: String): List<Song>

    @Query("SELECT * FROM song WHERE track_number LIKE :track ORDER BY track_number")
    fun findSongByTrack(track: String): List<Song>

    @Query("SELECT * FROM song WHERE songId=:id")
    fun findSongById(id: Int): Song

    @Query("SELECT * FROM category WHERE parent_category IS NULL ORDER BY category_title")
    fun findMainCategories(): List<Category>

    @Query("SELECT * FROM song JOIN category ON song.songId=category.song_id WHERE parent_category=:categoryId")
    fun findSongInCategory(categoryId: Long): List<SongWithCategories>

    @Insert
    fun batchInsertSongs(allSongs: List<Song>)

    @Insert
    fun batchInsertCategories(allCategories: List<Category>)

    @Update
    fun updateSong(song: Song)

    @Delete
    fun deleteSong(song: Song)
}