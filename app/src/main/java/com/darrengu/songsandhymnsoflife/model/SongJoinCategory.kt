package com.darrengu.songsandhymnsoflife.model

import android.arch.persistence.room.*

@Entity(tableName = "song_join_category",
        foreignKeys = [
            ForeignKey(
                    entity = Song::class,
                    parentColumns = ["song_id"],
                    childColumns = ["songId"]
                    ),
            ForeignKey(entity = Category::class,
                    parentColumns = ["category_id"],
                    childColumns = ["categoryId"])
        ])
data class SongJoinCategory(
        @PrimaryKey()
        var joinId: Long,
        var songId: Long,
        var categoryId: Long)