package com.tiooooo.mymovie.pages.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionMenuViewPager(
    fragmentManagers: FragmentManager,
    lifeCycle: Lifecycle,
    private val listFragments: List<Fragment>,
) : FragmentStateAdapter(fragmentManagers, lifeCycle) {

    override fun getItemCount() = listFragments.size

    override fun createFragment(position: Int): Fragment {
        return listFragments[position]
    }
}
