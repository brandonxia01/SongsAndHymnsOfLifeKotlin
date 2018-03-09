package com.darrengu.songsandhymnsoflife.fragment

import android.arch.lifecycle.Observer
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darrengu.songsandhymnsoflife.R
import com.darrengu.songsandhymnsoflife.model.Song
import com.darrengu.songsandhymnsoflife.viewmodel.ViewModelScoreActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_viewer.*
import org.jetbrains.anko.bundleOf
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.custom_playback_control_view.*
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import android.util.Log
import android.widget.ImageButton
import com.squareup.picasso.Callback
import com.squareup.picasso.Target
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception


/**
 * Created by darren.gu on 3/7/18.
 */
class FragViewer : Fragment() {
    private lateinit var viewModel: ViewModelScoreActivity
    var exoPlayer: ExoPlayer? = null
    var song: Song? = null
    var displayingBitmap: Bitmap? = null
    var scoreMode = true

    companion object {
        const val SONG_ID = "Song_Id"

        fun newInstance(songId: Long): FragViewer = FragViewer().apply { arguments = bundleOf(SONG_ID to songId) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelScoreActivity.newInstance(activity!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_viewer, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adaptiveTrackSelectionFactory = AdaptiveTrackSelection.Factory(DefaultBandwidthMeter())
        val trackSelector = DefaultTrackSelector(adaptiveTrackSelectionFactory)

        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
        val dataSourceFactory = DefaultDataSourceFactory(view.context, Util.getUserAgent(context, getString(R.string.app_name)))
        playerControlView.player = exoPlayer
        val target = object: Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Log.d("picasso", "asdf")
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                displayingBitmap = bitmap
                photoView.setImageBitmap(displayingBitmap)
                playerControlView.show()
            }
        }

        viewModel.song.observe(this, Observer {
            it?.let {
                Picasso.get()
                        .load(it.url)
                        .into(target)

                if (it.lyrics.isNotEmpty()) {
                    lyricsView.text = it.lyrics
                    flipButton.visibility = View.VISIBLE
                }

                val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse("http://www.sample-videos.com/audio/mp3/crowd-cheering.mp3"))
                exoPlayer?.prepare(mediaSource)
                exoPlayer?.playWhenReady = false
            }
        })

        shareButton.setOnClickListener { view ->
            displayingBitmap?.let {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "image/jpg"
                shareIntent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(view.context, it))
                startActivity(Intent.createChooser(shareIntent, "Share image using"))
            }
        }

        flipButton.setOnClickListener {
            if (song?.lyrics?.isNotEmpty() == true) {
                if (scoreMode) {
                    photoView.visibility = View.GONE
                    scrollView.visibility = View.VISIBLE
                    (it as ImageButton).setImageResource(R.drawable.ic_audiotrack_white_24dp)
                } else {
                    photoView.visibility = View.VISIBLE
                    scrollView.visibility = View.GONE
                    (it as ImageButton).setImageResource(R.drawable.ic_text_format_white_24dp)
                }
                scoreMode = !scoreMode
            }
        }

        photoView.setOnClickListener {
            if (playerControlView.isVisible) playerControlView.hide() else playerControlView.show()
        }
        scrollView.setOnClickListener {
            if (playerControlView.isVisible) playerControlView.hide() else playerControlView.show()
        }

        arguments?.getLong(SONG_ID)?.let { viewModel.findSongById(it) }
    }

    private fun getLocalBitmapUri(context: Context, bmp: Bitmap): Uri? {
        var bmpUri: Uri? = null
        try {
            val file = File(context.filesDir, "songs${song?.trackNumber}.png")
            FileOutputStream(file).use { bmp.compress(Bitmap.CompressFormat.PNG, 90, it) }
            bmpUri = Uri.fromFile(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }
}