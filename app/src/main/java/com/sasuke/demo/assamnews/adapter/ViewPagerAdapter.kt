package com.sasuke.demo.assamnews.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sasuke.demo.assamnews.activity.TempActivity

class ViewPagerAdapter(fragmentManager: FragmentManager, private val count: Int) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT ) {

    override fun getCount(): Int {
        return count
    }

    override fun getItem(position: Int): Fragment {
        return TempActivity.EmptyFragment.newInstance(position)
    }
}