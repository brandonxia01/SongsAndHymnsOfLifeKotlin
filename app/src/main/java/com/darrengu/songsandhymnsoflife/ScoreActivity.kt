package com.darrengu.songsandhymnsoflife

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

/**
 * Created by darren.gu on 3/7/18.
 */
class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("score", "id=" + intent.getLongExtra("songId", -1))
    }
}