package com.darrengu.songsandhymnsoflife.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.darrengu.songsandhymnsoflife.R
import com.darrengu.songsandhymnsoflife.viewmodel.ViewModelScoreActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_viewer.photoView
import org.jetbrains.anko.bundleOf

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
        viewModel = ViewModelScoreActivity.newInstance(requireActivity())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_viewer, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.song.observe(viewLifecycleOwner, Observer {
            it?.url?.let {
                Picasso.get()
                        .load(it)
                        .into(photoView)
            }
        })
        arguments?.getLong(SONG_ID)?.let { viewModel.findSongById(it) }
    }

}