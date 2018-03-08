package com.darrengu.songsandhymnsoflife

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.darrengu.songsandhymnsoflife.fragment.FragViewer

/**
 * Created by darren.gu on 3/7/18.
 */
class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        val viewerFragment = FragViewer.newInstance(intent.getLongExtra(FragViewer.SONG_ID, 0))
        supportFragmentManager.beginTransaction()
                .add(R.id.scoreContainer, viewerFragment)
                .commit()
    }
}