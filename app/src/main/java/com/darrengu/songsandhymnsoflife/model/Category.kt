package com.darrengu.songsandhymnsoflife.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by darren.gu on 3/5/18.
 */
@Entity
class Category(@Id var id: Long = 0, var categoryTitle: String = "")