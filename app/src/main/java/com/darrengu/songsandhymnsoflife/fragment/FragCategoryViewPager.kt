package com.darrengu.songsandhymnsoflife.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darrengu.songsandhymnsoflife.R
import com.darrengu.songsandhymnsoflife.adapter.AdapterPagerCategory
import kotlinx.android.synthetic.main.fragment_category_pager.*
import org.jetbrains.anko.bundleOf

/**
 * Created by darren.gu on 3/8/18.
 */
class FragCategoryViewPager : BaseFragmentMainActivity() {
    companion object {
        private const val CATEGORY_ID = "categoryId"

        fun newInstance(id: Long): FragCategoryViewPager {
            val fragment = FragCategoryViewPager()
            fragment.arguments = bundleOf(CATEGORY_ID to id)
            return fragment
        }
    }

    private var categoryId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryId = arguments?.getLong(CATEGORY_ID)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        categoryList.layoutManager = LinearLayoutManager(context)
        categoryId?.let {
            val categoriesWithSongs = viewModel.findSongsInCategory(it)
            val flattenList = mutableListOf<Any>()
            categoriesWithSongs?.map {
                it.category?.let { flattenList.add(it) }
                flattenList.addAll(it.songs)
            }
            val adapter = AdapterPagerCategory(flattenList) {
                Log.d("onClickSong", it.toString())
            }
            categoryList.adapter = adapter
            Log.d("result", categoriesWithSongs?.size.toString())
        }
    }






}