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
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
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
    private var flatList: List<Any> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryId = arguments?.getLong(CATEGORY_ID)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        categoryList.layoutManager = LinearLayoutManager(context)

        launch(UI) {
            if (flatList.isEmpty()) {
                categoryId?.let {
                    flatList = async {
                        viewModel.findSongsInCategory(it)
                    }.await()
                }
            }
            categoryList.adapter = AdapterPagerCategory(flatList) { startScoreActivity(it) }
        }
    }
}