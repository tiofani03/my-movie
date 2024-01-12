package com.tiooooo.mymovie.pages.main.movie

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiooooo.core.base.BaseFragment
import com.tiooooo.core.extensions.collectFlow
import com.tiooooo.core.network.data.States
import com.tiooooo.mymovie.R
import com.tiooooo.mymovie.databinding.FragmentMovieBinding
import com.tiooooo.mymovie.pages.main.MainActivity
import com.tiooooo.mymovie.pages.main.movie.adapter.genre.GenreContainerAdapter
import com.tiooooo.mymovie.pages.main.movie.adapter.poster.PosterContainerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentMovieBinding, MainActivity>(R.layout.fragment_movie) {
    private val movieViewModel: MovieViewModel by viewModels()

    // parent adapter
    private lateinit var concatAdapter: ConcatAdapter

    private val genreContainerAdapter = GenreContainerAdapter()
    private val nowPlayingContainerAdapter = PosterContainerAdapter("Now Playing")
    private val popularContainerAdapter = PosterContainerAdapter("Popular")
    private val topRatedContainerAdapter = PosterContainerAdapter("Top Rated")
    private val upComingContainerAdapter = PosterContainerAdapter("Up Coming")

    override fun initView() {
        super.initView()
        setupAdapter()
        movieViewModel.getGenres()
        movieViewModel.getNowPlaying()
        movieViewModel.getPopular()
        movieViewModel.getTopRated()
        movieViewModel.getUpComing()
    }

    private fun setupAdapter() {
        concatAdapter = ConcatAdapter(genreContainerAdapter)
        concatAdapter.addAdapter(nowPlayingContainerAdapter)
        concatAdapter.addAdapter(popularContainerAdapter)
        concatAdapter.addAdapter(topRatedContainerAdapter)
        concatAdapter.addAdapter(upComingContainerAdapter)

        binding.rvContainer.adapter = concatAdapter
        binding.rvContainer.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun setSubscribeToLiveData() {
        collectFlow(movieViewModel.genres){
            when(it){
                is States.Success -> {
                    genreContainerAdapter.setData(it.data.genreList)
                }
                else -> {

                }
            }
        }

        collectFlow(movieViewModel.nowPlaying){
            when(it){
                is States.Success -> {
                    nowPlayingContainerAdapter.setData(it.data)
                }
                else -> {
                }
            }
        }

        collectFlow(movieViewModel.popular){
            when(it){
                is States.Success -> {
                    popularContainerAdapter.setData(it.data)
                }
                else -> {
                }
            }
        }

        collectFlow(movieViewModel.topRated){
            when(it){
                is States.Success -> {
                    topRatedContainerAdapter.setData(it.data)
                }
                else -> {
                }
            }
        }

        collectFlow(movieViewModel.upComing){
            when(it){
                is States.Success -> {
                    upComingContainerAdapter.setData(it.data)
                }
                else -> {
                }
            }
        }
    }

}
