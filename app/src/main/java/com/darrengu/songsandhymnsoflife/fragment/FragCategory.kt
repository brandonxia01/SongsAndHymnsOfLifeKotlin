package com.darrengu.songsandhymnsoflife.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darrengu.songsandhymnsoflife.R
import com.darrengu.songsandhymnsoflife.adapter.PageAdapterCategory
import kotlinx.android.synthetic.main.fragment_category.*

/**
 * Created by darren.gu on 3/8/18.
 */
class FragCategory : BaseFragmentMainActivity() {
    var adapter: PageAdapterCategory? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.mainCategories.observe(this, Observer {
            val titles = mutableListOf<String>()
            val fragments = it?.map {
                titles.add(it.categoryTitle?:"")
                FragCategoryViewPager.newInstance(it.categoryId)
            }
            fragments?.let {
                viewPager.adapter = PageAdapterCategory(activity!!.supportFragmentManager,
                        it, titles)
                tabLayout.setupWithViewPager(viewPager)
            }
        })

        viewModel.findMainCategories()
    }
}