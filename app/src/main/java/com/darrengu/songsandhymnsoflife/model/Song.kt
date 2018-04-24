package com.darrengu.songsandhymnsoflife.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Created by darren.gu on 3/4/18.
 */
@Entity(tableName = "song")
data class Song(
        @PrimaryKey()
        @ColumnInfo(name = "song_id")
        var songId: Long,
        @ColumnInfo(name = "track_number")
        var trackNumber: Long?,
        @ColumnInfo(name = "title")
        var title: String?,
        @ColumnInfo(name = "language")
        var language: String = "English",
        @ColumnInfo(name = "url_score")
        var urlScore: String? = null,
        @ColumnInfo(name = "url_tune")
        var urlTune: String? = null,
        @ColumnInfo(name = "sub_index")
        var subIndex: String? = "pinyin",
        @ColumnInfo(name = "lyrics")
        var lyrics: String? = null
)