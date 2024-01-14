package com.tiooooo.mymovie.pages.detail.movie

import android.content.Intent
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.tiooooo.core.base.BaseActivity
import com.tiooooo.core.extensions.collectFlow
import com.tiooooo.core.extensions.gone
import com.tiooooo.core.extensions.setCollapsing
import com.tiooooo.core.extensions.visible
import com.tiooooo.data.movie.api.model.casts.Cast
import com.tiooooo.data.movie.api.model.detail.MovieDetail
import com.tiooooo.data.movie.api.model.review.MovieReview
import com.tiooooo.data.movie.api.model.video.MovieVideo
import com.tiooooo.mymovie.R
import com.tiooooo.mymovie.databinding.ActivityDetailMovieBinding
import com.tiooooo.mymovie.pages.detail.movie.adapter.CastAdapter
import com.tiooooo.mymovie.pages.detail.movie.adapter.GenreDetailAdapter
import com.tiooooo.mymovie.pages.detail.movie.adapter.ReviewAdapter
import com.tiooooo.mymovie.pages.detail.movie.adapter.VideoAdapter
import com.tiooooo.mymovie.pages.review.ListReviewActivity
import com.tiooooo.mymovie.pages.videoPlayer.VideoPlayerActivity
import com.tiooooo.mymovie.pages.webview.WebViewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : BaseActivity<ActivityDetailMovieBinding>() {
    companion object {
        const val EXTRA_ID = "EXTRA_ID"
    }

    override fun getViewBinding() = ActivityDetailMovieBinding.inflate(layoutInflater)

    private val detailMovieViewModel: DetailMovieViewModel by viewModels()
    private lateinit var movieId: String

    override fun initView() {
        movieId = intent.getStringExtra(EXTRA_ID) ?: "0"
        setupToolbar(binding.toolbar)
        if (movieId.isNotEmpty()) {
            with(detailMovieViewModel) {
                getMovieDetail(movieId)
                getMovieCast(movieId)
                getMovieReview(movieId)
                getMovieVideo(movieId)
            }
        }
    }

    override fun setViewModelObservable() {
        collectFlow(detailMovieViewModel.movie) { state ->
            binding.apply {
                handleDataState(
                    state = state,
                    loadingBlock = { showContent(false) },
                    successBlock = {
                        showContent(true)
                        setMovie(it)
                        setCollapsing(
                            it.title,
                            binding.collapsingToolbar,
                            binding.tvTitle,
                            binding.appbar,
                        )
                    },
                    errorBlock = { showErrorContent(it) },
                    emptyBlock = { showErrorContent() },
                )
            }
        }
        collectFlow(detailMovieViewModel.cast) { state ->
            binding.apply {
                val castView = listOf(
                    contentDetail.shimmerCasts,
                    contentDetail.rvCasts,
                    contentDetail.tvCasts
                )
                handleDataState(
                    state = state,
                    loadingBlock = { showCast(false) },
                    successBlock = {
                        showCast(true)
                        initCastAdapter(it)
                        contentDetail.rvCasts.isVisible = it.isNotEmpty()
                        contentDetail.tvCasts.isVisible = it.isNotEmpty()
                    },
                    errorBlock = { castView.gone() },
                    emptyBlock = { castView.gone() },
                )
            }
        }
        collectFlow(detailMovieViewModel.review) { state ->
            binding.apply {
                val reviewView = listOf(
                    contentDetail.rvReview,
                    contentDetail.tvReviews,
                    contentDetail.ivReviewDetail
                )
                handleDataState(
                    state = state,
                    loadingBlock = { showReview(false) },
                    successBlock = {
                        showReview(true)
                        initReviewAdapter(it)
                        reviewView.isVisible(it.isNotEmpty())
                    },
                    errorBlock = { reviewView.gone() },
                    emptyBlock = { reviewView.gone() },
                )
            }
        }
        collectFlow(detailMovieViewModel.videos) { state ->
            binding.apply {
                val videoView = listOf(
                    contentDetail.shimmerVideo,
                    contentDetail.rvVideo,
                    contentDetail.tvTrailer
                )
                handleDataState(
                    state = state,
                    loadingBlock = { showVideo(false) },
                    successBlock = {
                        val data = it.filter { video -> video.site == "YouTube" }
                        if (data.isNotEmpty()) {
                            showVideo(true)
                            initVideoAdapter(it)
                        } else {
                            videoView.gone()
                        }
                    },
                    errorBlock = { videoView.gone() },
                    emptyBlock = { videoView.gone() },
                )
            }
        }
    }

    private fun showContent(state: Boolean) {
        binding.appbar.isVisible = state
        binding.nestedScrollView.isVisible = state
        binding.layoutLoading.root.isVisible = !state
    }

    private fun showErrorContent(message: String? = getString(R.string.error_message)) {
        binding.apply {
            appbar.gone()
            nestedScrollView.gone()
            layoutLoading.root.gone()
        }
        binding.layoutError.apply {
            root.visible()
            tvInfo.text = message
            btnTryAgain.setOnClickListener {
                initView()
                root.gone()
            }
        }
    }

    private fun setMovie(data: MovieDetail) {
        binding.apply {
            ivBackDrop.load(data.createBackdropPath()) {
                crossfade(true)
                placeholder(R.drawable.ic_image)
                error(R.drawable.ic_image)
            }
            contentTitle.apply {
                tvTitleTv.text = data.createTitleWithYear()
                tvRatingCount.text = data.createVoteCountToString()

                val genreAdapter = GenreDetailAdapter(data.genres)
                rvGenre.apply {
                    adapter = genreAdapter
                    layoutManager =
                        LinearLayoutManager(
                            this@DetailMovieActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                }
            }
            contentDetail.ivReviewDetail.setOnClickListener {
                val intent =
                    Intent(this@DetailMovieActivity, ListReviewActivity::class.java).apply {
                        putExtra(ListReviewActivity.EXTRA_ID, data.id.toString())
                        putExtra(ListReviewActivity.EXTRA_TITLE, "Review of ${data.title}")
                    }
                startActivity(intent)
            }

            contentDetail.apply {
                tvDescDetail.text = data.overview
                tvReleaseDate.text = data.createDateString()
            }
        }
    }

    private fun showCast(state: Boolean) {
        binding.contentDetail.apply {
            shimmerCasts.isVisible = !state
            rvCasts.isVisible = state
        }
    }

    private fun initCastAdapter(data: List<Cast>) {
        val castAdapter = CastAdapter(data)
        binding.contentDetail.rvCasts.apply {
            layoutManager =
                LinearLayoutManager(this@DetailMovieActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = castAdapter
        }
    }

    private fun showReview(state: Boolean) {
        binding.contentDetail.apply {
            shimmerReview.isVisible = !state
            rvReview.isVisible = state
            ivReviewDetail.isVisible = state
        }
    }

    private fun initReviewAdapter(data: List<MovieReview>) {
        val reviewAdapter = ReviewAdapter(data.takeLast(3)).apply {
            onItemClick = {
                val intent = Intent(this@DetailMovieActivity, WebViewActivity::class.java).apply {
                    putExtra(WebViewActivity.BASE_URL, it)
                }
                startActivity(intent)
            }
        }
        binding.contentDetail.rvReview.apply {
            layoutManager = LinearLayoutManager(this@DetailMovieActivity)
            adapter = reviewAdapter
        }
    }

    private fun showVideo(state: Boolean) {
        binding.contentDetail.apply {
            shimmerVideo.isVisible = !state
            rvVideo.isVisible = state
        }
    }

    private fun initVideoAdapter(data: List<MovieVideo>) {
        val videoAdapter = VideoAdapter(data.filter { it.site == "YouTube" }).apply {
            onItemClick = {
                val intent =
                    Intent(this@DetailMovieActivity, VideoPlayerActivity::class.java).apply {
                        putExtra(VideoPlayerActivity.EXTRA_KEY, it)
                    }
                startActivity(intent)
            }
        }
        binding.contentDetail.rvVideo.apply {
            layoutManager =
                LinearLayoutManager(this@DetailMovieActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = videoAdapter
        }
    }
}
