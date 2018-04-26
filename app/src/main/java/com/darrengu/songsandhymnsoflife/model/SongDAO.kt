package com.darrengu.songsandhymnsoflife.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface SongDAO {
    @Query("SELECT * FROM song WHERE song_id ORDER BY track_number")
    fun getAllByTrackNumber(): List<Song>

    @Query("SELECT * FROM song WHERE song_id ORDER BY sub_index")
    fun getAllByAlphabeticalOrder(): List<Song>

    @Query("SELECT * FROM song WHERE title LIKE :keyword OR lyrics LIKE :keyword")
    fun search(keyword: String): List<Song>

    @Query("SELECT * FROM song WHERE track_number LIKE :track ORDER BY track_number")
    fun findSongByTrack(track: String): List<Song>

    @Query("SELECT * FROM song WHERE song_id=:id LIMIT 1")
    fun findSongById(id: Long): Song

    @Query("SELECT * FROM category WHERE parent_category IS NULL ORDER BY category_title")
    fun findMainCategories(): List<Category>

    @Query("SELECT * FROM category JOIN song_join_category ON category_id=categoryId JOIN song ON song_id=songId WHERE parent_category=:categoryId ORDER BY track_number")
    fun findSongInCategory(categoryId: Long): List<SongCategory>

    @Insert
    fun batchInsertSongs(allSongs: List<Song>)

    @Insert
    fun batchInsertCategories(allCategories: List<Category>)

    @Insert
    fun batchInsertSongCategoryRelations(allRelations: List<SongJoinCategory>)

    @Update
    fun updateSong(song: Song)

    @Delete
    fun deleteSong(song: Song)
}