package com.tiooooo.mymovie.pages.main.movie

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiooooo.core.base.BaseFragment
import com.tiooooo.core.extensions.collectFlow
import com.tiooooo.core.network.data.States
import com.tiooooo.data.movie.api.model.list.Genre
import com.tiooooo.mymovie.R
import com.tiooooo.mymovie.databinding.FragmentMovieBinding
import com.tiooooo.mymovie.pages.detail.movie.DetailMovieActivity
import com.tiooooo.mymovie.pages.genre.GenreActivity
import com.tiooooo.mymovie.pages.list.ListMovieActivity
import com.tiooooo.mymovie.pages.main.MainActivity
import com.tiooooo.mymovie.pages.main.movie.adapter.genre.GenreContainerAdapter
import com.tiooooo.mymovie.pages.main.movie.adapter.poster.PosterContainerAdapter
import com.tiooooo.mymovie.pages.main.movie.listener.GenreListener
import com.tiooooo.mymovie.pages.main.movie.listener.PosterListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentMovieBinding, MainActivity>(R.layout.fragment_movie) {
    private val movieViewModel: MovieViewModel by viewModels()

    // parent adapter
    private lateinit var concatAdapter: ConcatAdapter

    private val genreContainerAdapter = GenreContainerAdapter(handleGenreListener())
    private val nowPlayingContainerAdapter =
        PosterContainerAdapter("Now Playing", "now_playing", handlePosterListener())
    private val popularContainerAdapter =
        PosterContainerAdapter("Popular", "popular", handlePosterListener())
    private val topRatedContainerAdapter = PosterContainerAdapter(
        "Top Rated",
        "top_rated",
        handlePosterListener()
    )
    private val upComingContainerAdapter = PosterContainerAdapter(
        "Up Coming",
        "upcoming",
        handlePosterListener()
    )

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
        collectFlow(movieViewModel.genres) {
            when (it) {
                is States.Success -> {
                    genreContainerAdapter.setData(it.data.genreList)
                }

                else -> {

                }
            }
        }

        collectFlow(movieViewModel.nowPlaying) {
            when (it) {
                is States.Success -> {
                    nowPlayingContainerAdapter.setData(it.data)
                }

                else -> {
                }
            }
        }

        collectFlow(movieViewModel.popular) {
            when (it) {
                is States.Success -> {
                    popularContainerAdapter.setData(it.data)
                }

                else -> {
                }
            }
        }

        collectFlow(movieViewModel.topRated) {
            when (it) {
                is States.Success -> {
                    topRatedContainerAdapter.setData(it.data)
                }

                else -> {
                }
            }
        }

        collectFlow(movieViewModel.upComing) {
            when (it) {
                is States.Success -> {
                    upComingContainerAdapter.setData(it.data)
                }

                else -> {
                }
            }
        }
    }

    private fun handlePosterListener(): PosterListener {
        return object : PosterListener {
            override fun onClickSeeAll(title: String, type: String) {
                val intent = Intent(requireActivity(), ListMovieActivity::class.java).apply {
                    putExtra("EXTRA_TITLE", title)
                    putExtra("EXTRA_TYPE", type)
                }
                startActivity(intent)
            }

            override fun onClickDetail(movieId: Long) {
                val intent = Intent(requireActivity(), DetailMovieActivity::class.java).apply {
                    putExtra(DetailMovieActivity.EXTRA_ID, movieId.toString())
                }
                startActivity(intent)
            }
        }
    }

    private fun handleGenreListener(): GenreListener {
        return object : GenreListener {
            override fun onClick(list: ArrayList<Genre>, position: Int) {
                val intent = Intent(requireActivity(), GenreActivity::class.java).apply {
                    putParcelableArrayListExtra(GenreActivity.EXTRA_GENRE, list)
                    putExtra(GenreActivity.EXTRA_POSITION, position)
                }
                startActivity(intent)
            }
        }
    }

}
