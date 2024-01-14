package com.tiooooo.mymovie.pages.review

import android.content.Intent
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiooooo.core.base.BaseActivity
import com.tiooooo.core.extensions.getLaunch
import com.tiooooo.mymovie.databinding.ActivityListReviewBinding
import com.tiooooo.mymovie.pages.webview.WebViewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ListReviewActivity : BaseActivity<ActivityListReviewBinding>() {
    companion object {
        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_TITLE = "EXTRA_TITLE"
    }

    private val listReviewModel: ListReviewViewModel by viewModels()
    private lateinit var listReviewAdapter: ListAllReviewAdapter
    private lateinit var title: String
    private lateinit var movieId: String

    override fun getViewBinding() = ActivityListReviewBinding.inflate(layoutInflater)

    override fun initView() {
        title = intent.getStringExtra(EXTRA_TITLE) ?: "Popular"
        movieId = intent.getStringExtra(EXTRA_ID) ?: "popular"
        binding.toolbar.title = title
        setupToolbar(binding.toolbar)
        listReviewModel.getMovieReview(movieId)

        listReviewAdapter = ListAllReviewAdapter().apply {
            onItemClick = {
                val intent = Intent(this@ListReviewActivity, WebViewActivity::class.java).apply {
                    putExtra(WebViewActivity.BASE_URL, it)
                }
                startActivity(intent)
            }
        }
        binding.rvReview.apply {
            this.adapter = listReviewAdapter
            layoutManager = LinearLayoutManager(this@ListReviewActivity)
        }
    }

    override fun initListener() {
        binding.apply {
            swipeRefresh.setOnRefreshListener {
                listReviewModel.getMovieReview(movieId)
                swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun setViewModelObservable() {
        super.setViewModelObservable()
        listReviewModel.reviews.observe(this) {
            getLaunch {
                listReviewAdapter.submitData(it)
            }
        }

        getLaunch {
            listReviewAdapter.loadStateFlow.collectLatest {
                with(binding) {
                    shimmerReview.isVisible = it.refresh is LoadState.Loading
                    progressBarLoadMore.isVisible = it.append is LoadState.Loading
                    rvReview.isVisible =
                        it.refresh !is LoadState.Loading && it.refresh !is LoadState.Error
                }
            }
        }
    }
}
