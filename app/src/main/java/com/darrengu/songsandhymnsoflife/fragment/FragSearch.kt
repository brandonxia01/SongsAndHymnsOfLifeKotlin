package com.darrengu.songsandhymnsoflife.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darrengu.songsandhymnsoflife.R
import com.darrengu.songsandhymnsoflife.adapter.AdapterGenericRecyclerSong
import com.darrengu.songsandhymnsoflife.model.Song
import com.darrengu.songsandhymnsoflife.viewmodel.ViewModelMainActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.concurrent.TimeUnit
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import io.reactivex.disposables.Disposable


/**
 * Created by darren.gu on 3/4/18.
 */
class FragSearch : Fragment() {
    companion object {
        val TAG = FragSearch::class.java.simpleName
    }
    private lateinit var sharedModelMainActivity: ViewModelMainActivity
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedModelMainActivity = ViewModelProviders.of(activity!!)[ViewModelMainActivity::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = AdapterGenericRecyclerSong()
        searchResults.layoutManager = LinearLayoutManager(context)
        searchResults.adapter = adapter
        sharedModelMainActivity.songInSearch.observe(this,
                Observer { results: List<Song>? -> results?.let { adapter.dataSet = it.toMutableList() } })
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
        disposable = RxTextView.textChanges(searchInput)
                .doOnNext { adapter.dataSet = mutableListOf() }
                .debounce(400, TimeUnit.MILLISECONDS)
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { sharedModelMainActivity.search(it.toString()) }
    }

    override fun onPause() {
        if (isRemoving && disposable?.isDisposed == false) {
            disposable?.dispose()
        }
        super.onPause()
    }
}