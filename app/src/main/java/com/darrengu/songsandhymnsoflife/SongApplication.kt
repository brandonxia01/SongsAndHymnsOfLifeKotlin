package com.darrengu.songsandhymnsoflife

import android.app.Application
import com.darrengu.songsandhymnsoflife.model.MyObjectBox
import io.objectbox.BoxStore

/**
 * Created by darren.gu on 3/5/18.
 */
class SongApplication : Application() {
    lateinit var boxStore: BoxStore

    companion object {
        lateinit var instance: SongApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        boxStore = MyObjectBox.builder().androidContext(this).build()

    }
}