package com.darrengu.songsandhymnsoflife.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

/**
 * Created by darren.gu on 3/4/18.
 */
@Entity
class Song(@Id var id: Long = 0, var trackNumber: String = "", var title: String = "",
           var language: String = "English", var url: String = "") {
    lateinit var categories: ToMany<Category>
}