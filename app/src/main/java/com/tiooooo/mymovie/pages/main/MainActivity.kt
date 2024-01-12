package com.tiooooo.mymovie.pages.main

import androidx.activity.viewModels
import com.tiooooo.core.base.BaseActivity
import com.tiooooo.core.extensions.collectFlow
import com.tiooooo.core.network.data.States
import com.tiooooo.mymovie.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {
        mainViewModel.getGenres()
    }

    override fun setViewModelObservable() {
        collectFlow(mainViewModel.genres) {
            when (it) {
                is States.Loading -> {
                    binding.tvState.text = "Loading"
                }

                is States.Success -> {
                    binding.tvState.text = it.data.genreList.toString() + it.data.genreList.size
                }

                is States.Error -> {
                    binding.tvState.text = it.message
                }

                else -> {
                    binding.tvState.text = "Else"
                }
            }
        }
    }

}
