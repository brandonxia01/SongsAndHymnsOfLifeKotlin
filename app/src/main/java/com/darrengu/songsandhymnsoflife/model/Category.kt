package com.darrengu.songsandhymnsoflife.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

/**
 * Created by darren.gu on 3/5/18.
 */
@Entity
class Category(@Id var id: Long = 0, var categoryTitle: String = "", var isSub: Boolean = false) {
     lateinit var subCategory: ToMany<Category>
}