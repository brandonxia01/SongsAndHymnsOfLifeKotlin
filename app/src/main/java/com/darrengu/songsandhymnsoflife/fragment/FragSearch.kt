package com.darrengu.songsandhymnsoflife.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrengu.songsandhymnsoflife.R
import com.darrengu.songsandhymnsoflife.adapter.AdapterGenericRecyclerSong
import com.darrengu.songsandhymnsoflife.model.Song
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_search.searchInput
import kotlinx.android.synthetic.main.fragment_search.searchResults
import kotlinx.android.synthetic.main.fragment_search.summaryText
import java.util.concurrent.TimeUnit

/**
 * Created by darren.gu on 3/4/18.
 */
class FragSearch : BaseFragmentMainActivity() {
    companion object {
        val TAG = FragSearch::class.java.simpleName
    }
    private var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = AdapterGenericRecyclerSong(startScoreActivity)
        searchResults.layoutManager = LinearLayoutManager(context)
        searchResults.adapter = adapter
        viewModel.songInSearch.observe(viewLifecycleOwner,
                Observer { results: List<Song>? -> results?.let {
                    summaryText.text = "${it.size} songs in total"
                    adapter.dataSet = it.toMutableList()
                } })
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
        disposable = RxTextView.textChanges(searchInput)
                .doOnNext {
                    summaryText.text = ""
                    adapter.dataSet = mutableListOf() }
                .debounce(400, TimeUnit.MILLISECONDS)
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { viewModel.search(it.toString()) }
    }

    override fun onPause() {
        if (isRemoving && disposable?.isDisposed == false) {
            disposable?.dispose()
        }
        super.onPause()
    }
}