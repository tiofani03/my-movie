package com.tiooooo.mymovie.pages.main

import androidx.activity.viewModels
import com.tiooooo.core.base.BaseActivity
import com.tiooooo.core.extensions.collectFlow
import com.tiooooo.mymovie.databinding.ActivityMainBinding
import com.tiooooo.mymovie.pages.main.favorite.FavoriteFragment
import com.tiooooo.mymovie.pages.main.movie.MovieFragment
import com.tiooooo.mymovie.pages.main.tvSeries.TvSeriesFragment
import dagger.hilt.android.AndroidEntryPoint
import me.ertugrul.lib.OnItemSelectedListener


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var currentPosition = 0
    private var doubleBackPressedOnce = false

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {
        val viewPagerAdapter = SectionMenuViewPager(
            supportFragmentManager, lifecycle, listOf(
                MovieFragment(),
                TvSeriesFragment(),
                FavoriteFragment(),
            )
        )
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.offscreenPageLimit = 5

        binding.bottomBar.setOnItemSelectListener(object : OnItemSelectedListener {
            override fun onItemSelect(pos: Int) {
                binding.apply {
                    viewPager.setCurrentItem(pos, false)
                    currentPosition = pos
                }
            }
        })
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
//        if (currentPosition == 0) {
//            val currentFragment = supportFragmentManager.fragments.firstOrNull()
//
//            if (currentFragment is MovieFragment && currentFragment.handleBackPressed()) {
//                if (doubleBackPressedOnce) {
//                    super.onBackPressed()
//                    return
//                }
//
//                doubleBackPressedOnce = true
//                Toast.makeText(this, "Tekan sekali lagi untuk Keluar", Toast.LENGTH_SHORT).show()
//                Handler(Looper.getMainLooper()).postDelayed({
//                    doubleBackPressedOnce = false
//                }, 2000)
//            }
//        } else {
//            if (doubleBackPressedOnce) {
//                super.onBackPressed()
//                return
//            }
//
//            doubleBackPressedOnce = true
//            Toast.makeText(this, "Tekan sekali lagi untuk Keluar", Toast.LENGTH_SHORT).show()
//            Handler(Looper.getMainLooper()).postDelayed({
//                doubleBackPressedOnce = false
//            }, 2000)
//        }
    }

}
