package com.tiooooo.mymovie.pages.list

import android.content.Intent
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.tiooooo.core.base.BaseActivity
import com.tiooooo.core.extensions.getLaunch
import com.tiooooo.mymovie.databinding.ActivityListMovieBinding
import com.tiooooo.mymovie.pages.detail.movie.DetailMovieActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ListMovieActivity : BaseActivity<ActivityListMovieBinding>() {
    companion object {
        const val EXTRA_TITLE = "EXTRA_TITLE title"
        const val EXTRA_TYPE = "EXTRA_TYPE"
    }

    override fun getViewBinding() = ActivityListMovieBinding.inflate(layoutInflater)

    private val listMovieViewModel: ListMovieViewModel by viewModels()
    private lateinit var listMovieAdapter: ListMovieAdapter
    private lateinit var title: String
    private lateinit var type: String

    override fun initView() {
        title = intent.getStringExtra(EXTRA_TITLE) ?: "Popular"
        type = intent.getStringExtra(EXTRA_TYPE) ?: "popular"
        binding.toolbar.title = title
        setupToolbar(binding.toolbar)
        listMovieViewModel.getMoviesByType(type)

        listMovieAdapter = ListMovieAdapter().apply {
            onItemClick = {
                val intent = Intent(this@ListMovieActivity, DetailMovieActivity::class.java).apply {
                    putExtra(DetailMovieActivity.EXTRA_ID, it)
                }
                startActivity(intent)
            }
        }
        binding.rvMovie.apply {
            this.adapter = listMovieAdapter
            layoutManager = GridLayoutManager(this@ListMovieActivity, 2)
        }
    }

    override fun initListener() {
        binding.apply {
            swipeRefresh.setOnRefreshListener {
                listMovieViewModel.getMoviesByType(type)
                swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun setViewModelObservable() {
        super.setViewModelObservable()
        listMovieViewModel.movies.observe(this) {
            getLaunch {
                listMovieAdapter.submitData(it)
            }
        }

        getLaunch {
            listMovieAdapter.loadStateFlow.collectLatest {
                with(binding) {
                    progressBar.isVisible = it.refresh is LoadState.Loading
                    progressBarLoadMore.isVisible = it.append is LoadState.Loading
                }
            }
        }
    }
}
