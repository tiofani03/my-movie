package com.tiooooo.mymovie.pages.genre.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tiooooo.data.movie.api.model.Genre
import com.tiooooo.mymovie.pages.genre.GenreFragment

class SectionGenreAdapter(
    activity: AppCompatActivity,
    private val listCategory: ArrayList<Genre>,
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = listCategory.size

    override fun createFragment(position: Int): Fragment {
        return GenreFragment.newInstance(listCategory, position)
    }

}
