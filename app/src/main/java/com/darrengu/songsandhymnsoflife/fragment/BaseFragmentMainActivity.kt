package com.darrengu.songsandhymnsoflife.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.darrengu.songsandhymnsoflife.ScoreActivity
import com.darrengu.songsandhymnsoflife.viewmodel.ViewModelMainActivity
import org.jetbrains.anko.startActivity
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager



/**
 * Created by darren.gu on 3/7/18.
 */
open class BaseFragmentMainActivity : Fragment() {
    protected lateinit var viewModel: ViewModelMainActivity
    val startScoreActivity = { songId: Long ->
        if (isConnected()) {
            context?.startActivity<ScoreActivity>(FragViewer.SONG_ID to songId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelMainActivity.newInstance(activity!!)
    }

    private fun isConnected(): Boolean {
        context?.let {
            val cm = it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true
        }
        return false
    }
}