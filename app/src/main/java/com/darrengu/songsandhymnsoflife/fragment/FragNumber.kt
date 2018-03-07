package com.darrengu.songsandhymnsoflife.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darrengu.songsandhymnsoflife.R
import com.darrengu.songsandhymnsoflife.model.Song
import com.darrengu.songsandhymnsoflife.viewmodel.ViewModelMainActivity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import kotlinx.android.synthetic.main.fragment_number.*
import kotlinx.android.synthetic.main.fragment_number.view.*

/**
 * Created by darren.gu on 3/4/18.
 */
class FragNumber : Fragment() {
    companion object {
        val TAG = FragNumber::class.java.simpleName
    }

    private lateinit var sharedModelMainActivity: ViewModelMainActivity
    private var disposable: Disposable? = null
    private var savedInput: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedModelMainActivity = ViewModelProviders.of(activity!!)[ViewModelMainActivity::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
                .subscribe { inputText.text = it }
    }

    override fun onPause() {
        if (isRemoving && disposable?.isDisposed == false) {
            disposable?.dispose()
        }
        savedInput = inputText.text.toString()
        super.onPause()
    }
}