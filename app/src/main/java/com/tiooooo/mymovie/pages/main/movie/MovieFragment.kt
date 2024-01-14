package com.tiooooo.mymovie.pages.main.movie

import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiooooo.core.base.BaseFragment
import com.tiooooo.core.extensions.collectFlow
import com.tiooooo.core.network.data.States
import com.tiooooo.core.network.data.handleStates
import com.tiooooo.data.movie.api.model.list.Genre
import com.tiooooo.data.movie.implementation.local.entity.SearchHistoryEntity
import com.tiooooo.mymovie.R
import com.tiooooo.mymovie.databinding.FragmentMovieBinding
import com.tiooooo.mymovie.pages.detail.movie.DetailMovieActivity
import com.tiooooo.mymovie.pages.genre.GenreActivity
import com.tiooooo.mymovie.pages.list.ListMovieActivity
import com.tiooooo.mymovie.pages.main.MainActivity
import com.tiooooo.mymovie.pages.main.movie.adapter.SearchHistoryAdapter
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

    private val searchHistoryAdapter = SearchHistoryAdapter()
    private var currentSearchHistory: List<SearchHistoryEntity> = listOf()

    override fun initView() {
        super.initView()
        setupAdapter()
        movieViewModel.getGenres()
        movieViewModel.getNowPlaying()
        movieViewModel.getPopular()
        movieViewModel.getTopRated()
        movieViewModel.getUpComing()
        binding.rvSearchHistory.apply {
            adapter = searchHistoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
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
        collectFlow(movieViewModel.genres) { state ->
            genreContainerAdapter.setLoading(state is States.Loading)
            state.handleStates(
                loadingBlock = {},
                successBlock = { genreContainerAdapter.setData(it.genreList) },
                emptyBlock = { concatAdapter.removeAdapter(genreContainerAdapter) },
                errorBlock = { concatAdapter.removeAdapter(genreContainerAdapter) }
            )
            checkAdapter()
        }

        collectFlow(movieViewModel.nowPlaying) { state ->
            nowPlayingContainerAdapter.setLoading(state is States.Loading)
            state.handleStates(
                loadingBlock = {},
                successBlock = { nowPlayingContainerAdapter.setData(it) },
                emptyBlock = { concatAdapter.removeAdapter(nowPlayingContainerAdapter) },
                errorBlock = { concatAdapter.removeAdapter(nowPlayingContainerAdapter) }
            )
            checkAdapter()
        }

        collectFlow(movieViewModel.popular) { state ->
            popularContainerAdapter.setLoading(state is States.Loading)
            state.handleStates(
                loadingBlock = {},
                successBlock = { popularContainerAdapter.setData(it) },
                emptyBlock = { concatAdapter.removeAdapter(popularContainerAdapter) },
                errorBlock = { concatAdapter.removeAdapter(popularContainerAdapter) }
            )
            checkAdapter()
        }

        collectFlow(movieViewModel.topRated) { state ->
            topRatedContainerAdapter.setLoading(state is States.Loading)
            state.handleStates(
                loadingBlock = {},
                successBlock = { topRatedContainerAdapter.setData(it) },
                emptyBlock = { concatAdapter.removeAdapter(topRatedContainerAdapter) },
                errorBlock = { concatAdapter.removeAdapter(topRatedContainerAdapter) }
            )
            checkAdapter()
        }

        collectFlow(movieViewModel.upComing) { state ->
            upComingContainerAdapter.setLoading(state is States.Loading)
            state.handleStates(
                loadingBlock = {},
                successBlock = { upComingContainerAdapter.setData(it) },
                emptyBlock = { concatAdapter.removeAdapter(upComingContainerAdapter) },
                errorBlock = { concatAdapter.removeAdapter(upComingContainerAdapter) }
            )
            checkAdapter()
        }

        movieViewModel.getSearchHistory().observe(this) {
            currentSearchHistory = it
            searchHistoryAdapter.setData(it)
        }
    }

    override fun initListener() {
        binding.apply {
            searchView.editText.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchBar.text = searchView.text
                    searchView.hide()
                    val query = searchView.text.toString()
                    if (query.isNotEmpty()) {
                        val searchHistory = SearchHistoryEntity(
                            id = System.currentTimeMillis(),
                            query = query,
                            lastUpdated = System.currentTimeMillis(),
                        )
                        movieViewModel.addSearchHistory(currentSearchHistory, searchHistory)
                        val intent =
                            Intent(requireActivity(), ListMovieActivity::class.java).apply {
                                putExtra(ListMovieActivity.EXTRA_TITLE, "Result for $query")
                                putExtra(ListMovieActivity.EXTRA_QUERY, query)
                            }
                        startActivity(intent)
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

            searchHistoryAdapter.apply {
                onClicked = {
                    searchBar.text = it.query
                    searchView.hide()
                    movieViewModel.addSearchHistory(currentSearchHistory, it)
                    val intent = Intent(requireActivity(), ListMovieActivity::class.java).apply {
                        putExtra(ListMovieActivity.EXTRA_TITLE, "Result for ${it.query}")
                        putExtra(ListMovieActivity.EXTRA_QUERY, it.query)
                    }
                    startActivity(intent)
                }
                onDeleteClicked = {
                    movieViewModel.deleteSearchHistory(searchHistory = it)
                }
            }
        }
    }

    private fun checkAdapter() {
        binding.nestedScrollView.isVisible = concatAdapter.adapters.isNotEmpty()
        binding.layoutError.root.isVisible = concatAdapter.adapters.isEmpty()
        binding.layoutError.btnTryAgain.setOnClickListener {
            initView()
        }
    }

    private fun handlePosterListener(): PosterListener {
        return object : PosterListener {
            override fun onClickSeeAll(title: String, type: String) {
                val intent = Intent(requireActivity(), ListMovieActivity::class.java).apply {
                    putExtra(ListMovieActivity.EXTRA_TITLE, title)
                    putExtra(ListMovieActivity.EXTRA_TYPE, type)
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

    fun handleBackPressed(): Boolean {
        if (binding.searchView.isShowing) {
            binding.searchView.hide()
            return false
        }
        return true
    }

}
