package com.darrengu.songsandhymnsoflife.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrengu.songsandhymnsoflife.R
import com.darrengu.songsandhymnsoflife.adapter.AdapterGenericRecyclerSong
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_number.backspace
import kotlinx.android.synthetic.main.fragment_number.enter
import kotlinx.android.synthetic.main.fragment_number.inputText
import kotlinx.android.synthetic.main.fragment_number.num0
import kotlinx.android.synthetic.main.fragment_number.num1
import kotlinx.android.synthetic.main.fragment_number.num2
import kotlinx.android.synthetic.main.fragment_number.num3
import kotlinx.android.synthetic.main.fragment_number.num4
import kotlinx.android.synthetic.main.fragment_number.num5
import kotlinx.android.synthetic.main.fragment_number.num6
import kotlinx.android.synthetic.main.fragment_number.num7
import kotlinx.android.synthetic.main.fragment_number.num8
import kotlinx.android.synthetic.main.fragment_number.num9
import kotlinx.android.synthetic.main.fragment_number.previewList
import kotlinx.android.synthetic.main.fragment_number.summaryText
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
        viewModel.songNumber.observe(viewLifecycleOwner, Observer { previewSongs -> previewSongs?.let {
            summaryText.text = "${it.size} songs in total"
            adapter.dataSet = it.toMutableList()
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
                RxView.clicks(backspace).map { "" })
                .scan(savedInput, { t1, t2 -> if (t2 == "" && t1.isNotEmpty()) t1.dropLast(1) else t1.plus(t2) })
                .doOnNext {
                    inputText.text = it
                    summaryText.text = ""
                    adapter.dataSet = mutableListOf()
                }
                .filter { it.isNotEmpty() }
                .subscribe {
                    viewModel.findSongByTrack(it)
                }

        enter.setOnClickListener {
            val song = viewModel.songNumber.value
            when (song?.size) {
                0 -> context?.toast("can't find any songs")
                1 -> startScoreActivity(song[0].id)
                else -> context?.toast("too many options")
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