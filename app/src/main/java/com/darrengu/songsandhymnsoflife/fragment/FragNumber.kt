package com.darrengu.songsandhymnsoflife.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darrengu.songsandhymnsoflife.R
import com.darrengu.songsandhymnsoflife.adapter.AdapterGenericRecyclerSong
import com.darrengu.songsandhymnsoflife.viewmodel.ViewModelMainActivity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_number.*
import org.jetbrains.anko.toast

/**
 * Created by darren.gu on 3/4/18.
 */
class FragNumber : BaseFragmentMainActivity() {
    companion object {
        val TAG = FragNumber::class.java.simpleName
    }

    private var disposable: Disposable? = null
    private var savedInput: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        previewList.layoutManager = LinearLayoutManager(context)
        val adapter = AdapterGenericRecyclerSong(startScoreActivity)
        previewList.adapter = adapter
        viewModel.songNumber.observe(this, Observer { previewSongs -> previewSongs?.let {
            summaryText.text = "${it.size} songs in total"
            adapter.submitList(it.toMutableList())
        }})
        disposable = Observable.mergeArray(RxView.clicks(num1).map { "1" },
                RxView.clicks(num2).map { "2" },
                RxView.clicks(num3).map { "3" },
                RxView.clicks(num4).map { "4" },
                RxView.clicks(num5).map { "5" },
                RxView.clicks(num6).map { "6" },
                RxView.clicks(num7).map { "7" },
                RxView.clicks(num8).map { "8" },
                RxView.clicks(num9).map { "9" },
                RxView.clicks(num0).map { "0" },
                RxView.clicks(backspace).map { "back" })
                .scan(savedInput, { t1, t2 ->
                    var result = t1
                    if (t2 == "back") {
                        if (t1.isNotEmpty()) result = t1.dropLast(1) //delete a character if not empty
                    } else {
                        if (t2 == "0") {
                            if (t1.isNotEmpty() && t1.length < 4) result = t1.plus(t2) //0 cannot be the prefix of inputs
                        } else if (t1.length < 4){
                            result = t1.plus(t2)
                        }
                    }
                    result
                })
                .doOnNext {
                    inputText.text = it
                    summaryText.text = ""
                    viewModel.songNumber.value = mutableListOf()
                }
                .filter { it.isNotEmpty() && (it.toInt() in 1..9999)}
                .subscribe {
                    viewModel.findSongByTrack(it)
                }

        enter.setOnClickListener {
            val song = viewModel.songNumber.value
            when (song?.size) {
                0, null -> context?.toast("can't find any songs")
                else -> startScoreActivity(song[0].songId)
            }
        }
    }

    override fun onPause() {
        if (isRemoving && disposable?.isDisposed == false) {
            disposable?.dispose()
        }
        savedInput = inputText.text.toString()
        super.onPause()
    }
}