package com.tiooooo.mymovie.pages.genre

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.tiooooo.core.base.BaseActivity
import com.tiooooo.data.movie.api.model.Genre
import com.tiooooo.mymovie.databinding.ActivityGenreBinding
import com.tiooooo.mymovie.pages.genre.adapter.SectionGenreAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreActivity : BaseActivity<ActivityGenreBinding>() {
    companion object {
        const val EXTRA_GENRE = "extra genre"
        const val EXTRA_POSITION = "extra position"
    }

    private var listCategory: List<Genre>? = null
    private var position: Int? = 0
    override fun getViewBinding() = ActivityGenreBinding.inflate(layoutInflater)

    override fun initView() {
        listCategory = intent.getParcelableArrayListExtra(EXTRA_GENRE)
        position = intent.getIntExtra(EXTRA_POSITION, 0)
        binding.toolbar.title = "Genres"
        setupToolbar(binding.toolbar)
        setTabLayout()
    }

    private fun setTabLayout() {
        with(binding) {

            val newAdapter = SectionGenreAdapter(
                this@GenreActivity,
                listCategory as ArrayList<Genre>
            )

            viewPager.adapter = newAdapter
            TabLayoutMediator(tab, viewPager) { tab, position ->
                tab.text = (listCategory as ArrayList<Genre>)[position].name
            }.attach()

            viewPager.currentItem = position ?: 0
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int,
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    if (position > 0 && positionOffset == 0.0f && positionOffsetPixels == 0) {
                        viewPager.layoutParams.height =
                            viewPager.getChildAt(0).height
                    }
                }
            })
        }
    }
}
