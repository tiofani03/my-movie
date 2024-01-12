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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentMovieBinding, MainActivity>(R.layout.fragment_movie) {
    private val movieViewModel: MovieViewModel by viewModels()

    // parent adapter
    private lateinit var concatAdapter: ConcatAdapter

    private val genreContainerAdapter = GenreContainerAdapter()

    override fun initView() {
        super.initView()
        setupAdapter()
        movieViewModel.getGenres()

    }

    private fun setupAdapter() {
        concatAdapter = ConcatAdapter(genreContainerAdapter)

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
    }

}
