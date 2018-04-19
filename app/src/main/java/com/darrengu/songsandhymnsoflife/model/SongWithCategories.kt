package com.darrengu.songsandhymnsoflife.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

data class SongWithCategories(
        @Embedded var song: Song? = null,
        @Relation(entity = SongJoinCategory::class, parentColumn = "categoryId", entityColumn = "category_id")
        var categories: List<Category> = emptyList())