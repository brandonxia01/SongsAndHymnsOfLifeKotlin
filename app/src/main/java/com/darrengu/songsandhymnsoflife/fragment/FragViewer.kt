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
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.custom_playback_control_view.*
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v4.content.FileProvider
import android.util.Log
import android.widget.ImageButton
import com.squareup.picasso.Target
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception


/**
 * Created by darren.gu on 3/7/18.
 */
class FragViewer : Fragment() {
    private lateinit var viewModel: ViewModelScoreActivity
    var exoPlayer: ExoPlayer? = null
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
        val target = object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Log.e("picasso", e.toString())
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
                        .load(it.urlScore)
                        .into(target)

                it.lyrics?.let {
                    lyricsView.text = it
                    flipButton.visibility = View.VISIBLE
                }

                val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse("https://www.sample-videos.com/audio/mp3/wave.mp3"))
                exoPlayer?.prepare(mediaSource)
                exoPlayer?.playWhenReady = false
            }
        })

        shareButton.setOnClickListener { view ->
            displayingBitmap?.let { bitmap ->
                val file1 = File(view.context.getExternalFilesDir(null), "images")
                if (!file1.mkdirs()) {
                    Log.e("bitmap saving", "Directory not created")
                }
                val file = File(file1, "${viewModel.song.value?.trackNumber}.png")
                Log.d("file", file.exists().toString())

                FileOutputStream(file).use { bitmap.compress(Bitmap.CompressFormat.PNG, 90, it) }
                Log.d("file output", file.exists().toString())
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    val uri = FileProvider.getUriForFile(view.context, "com.darrengu.fileprovider", file)
                    setDataAndType(uri, "image/png")
                }
                if (activity?.packageManager?.let { shareIntent.resolveActivity(it) } != null) {
                    startActivity(shareIntent)
                }
            }
        }

        flipButton.setOnClickListener {
            if (viewModel.song.value?.lyrics?.isNotEmpty() == true) {
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
}