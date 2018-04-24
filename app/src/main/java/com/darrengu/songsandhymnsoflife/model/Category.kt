package com.darrengu.songsandhymnsoflife.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Created by darren.gu on 3/5/18.
 */
@Entity(tableName = "category")//, primaryKeys = ["category_id", "song_id"])
data class Category(
        @PrimaryKey()
        @ColumnInfo(name = "category_id")
        var categoryId: Long,
        @ColumnInfo(name = "category_title")
        var categoryTitle: String? = null,
        @ColumnInfo(name = "parent_category")
        var parentCategory: Long? = null
)