package com.tiooooo.mymovie.pages.main

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.tiooooo.core.base.BaseActivity
import com.tiooooo.mymovie.R
import com.tiooooo.mymovie.databinding.ActivityMainBinding
import com.tiooooo.mymovie.pages.main.movie.MovieFragment
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
        if (currentPosition == 0) {
            val currentFragment = supportFragmentManager.fragments.firstOrNull()

            if (currentFragment is MovieFragment && currentFragment.handleBackPressed()) {
                if (doubleBackPressedOnce) {
                    super.onBackPressed()
                    return
                }

                doubleBackPressedOnce = true
                Toast.makeText(
                    this,
                    getString(R.string.text_press_once_again_to_close_app), Toast.LENGTH_SHORT
                ).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    doubleBackPressedOnce = false
                }, 2000)
            }
        } else {
            if (doubleBackPressedOnce) {
                super.onBackPressed()
                return
            }

            doubleBackPressedOnce = true
            Toast.makeText(
                this,
                getString(R.string.text_press_once_again_to_close_app), Toast.LENGTH_SHORT
            ).show()
            Handler(Looper.getMainLooper()).postDelayed({
                doubleBackPressedOnce = false
            }, 2000)
        }
    }

}
