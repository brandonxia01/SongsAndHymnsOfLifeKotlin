package com.darrengu.songsandhymnsoflife.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import com.darrengu.songsandhymnsoflife.fragment.FragCategoryViewPager

/**
 * Created by darren.gu on 3/8/18.
 */
class PageAdapterCategory(fragmentManager: FragmentManager,
                          private val fragments: List<FragCategoryViewPager>,
                          private val titles: List<String>)
    : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

}