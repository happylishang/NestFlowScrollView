package com.snail.labaffinity.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Author: lishang
 * Data: 17/1/5 下午2:10
 * Des:
 * version:
 */
class MyFragmentPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        return MyFragment.newInstance(position.toString())
    }

    override fun getCount(): Int {
        return 10
    }
}