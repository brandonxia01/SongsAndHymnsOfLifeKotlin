package com.darrengu.songsandhymnsoflife.model
import android.arch.persistence.room.Embedded

data class SongCategory(
    @Embedded
    var song: Song,
    @Embedded
    var category: Category
)