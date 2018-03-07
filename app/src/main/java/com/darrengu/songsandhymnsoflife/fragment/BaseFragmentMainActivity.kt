package com.darrengu.songsandhymnsoflife.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.darrengu.songsandhymnsoflife.ScoreActivity
import com.darrengu.songsandhymnsoflife.viewmodel.ViewModelMainActivity
import org.jetbrains.anko.startActivity

/**
 * Created by darren.gu on 3/7/18.
 */
open class BaseFragmentMainActivity : Fragment() {
    protected lateinit var viewModel: ViewModelMainActivity
    val startScoreActivity = { songId: Long -> context?.startActivity<ScoreActivity>("songId" to songId) ?: Unit}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelMainActivity.newInstance(activity!!)

    }
}