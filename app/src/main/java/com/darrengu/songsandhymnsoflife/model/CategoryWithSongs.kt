package com.darrengu.songsandhymnsoflife.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

data class CategoryWithSongs(@Embedded var category: Category? = null,
                             @Relation(entity = SongJoinCategory::class, parentColumn = "songId", entityColumn = "song_id")
                             var songs: List<Song> = emptyList())