package com.darrengu.songsandhymnsoflife.fragment

import android.arch.lifecycle.Observer
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darrengu.songsandhymnsoflife.R
import com.darrengu.songsandhymnsoflife.viewmodel.ViewModelScoreActivity
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_viewer.*
import org.jetbrains.anko.bundleOf
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter


/**
 * Created by darren.gu on 3/7/18.
 */
class FragViewer : Fragment() {
    private lateinit var viewModel: ViewModelScoreActivity
    companion object {
        const val SONG_ID = "Song_Id"

        fun newInstance(songId: Long): FragViewer = FragViewer().apply { arguments = bundleOf(SONG_ID to songId) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelScoreActivity.newInstance(activity!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_viewer, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.song.observe(this, Observer {
            it?.url?.let {
                Picasso.get()
                        .load(it)
                        .into(photoView)
            }

            //val mediaSource = ExtractorMediaSource.Factory()

        })
        arguments?.getLong(SONG_ID)?.let { viewModel.findSongById(it) }
//
//        val adaptiveTrackSelectionFactory = AdaptiveTrackSelection.Factory(DefaultBandwidthMeter())
//        val trackSelector = DefaultTrackSelector(adaptiveTrackSelectionFactory)
//        val trackSelectionHelper = TrackSelectionHelper(trackSelector, adaptiveTrackSelectionFactory)
//
//
//        val exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
//        exoPlayer.prepare()
//        exoPlayer.playWhenReady = false
    }

}