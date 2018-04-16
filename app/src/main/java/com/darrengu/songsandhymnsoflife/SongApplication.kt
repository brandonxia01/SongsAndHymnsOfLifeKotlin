package com.darrengu.songsandhymnsoflife

import android.app.Application
/**
 * Created by darren.gu on 3/5/18.
 */
class SongApplication : Application() {

    companion object {
        lateinit var instance: SongApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }
}