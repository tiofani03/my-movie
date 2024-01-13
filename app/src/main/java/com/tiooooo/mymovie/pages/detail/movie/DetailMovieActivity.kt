package com.tiooooo.mymovie.pages.detail.movie

import android.app.Activity
import android.content.Intent
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.tiooooo.core.base.BaseActivity
import com.tiooooo.core.extensions.collectFlow
import com.tiooooo.core.network.data.States
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
import com.tiooooo.mymovie.pages.videoPlayer.VideoPlayerActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

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
        if (movieId.isNotEmpty()) {
            detailMovieViewModel.getMovieDetail(movieId)
            detailMovieViewModel.getMovieCast(movieId)
            detailMovieViewModel.getMovieReview(movieId)
            detailMovieViewModel.getMovieVideo(movieId)
        }
    }

    override fun setViewModelObservable() {
        collectFlow(detailMovieViewModel.movie) {
            when (it) {
                is States.Loading -> {}
                is States.Success -> {
                    setMovie(it.data)
                    setCollapsing(
                        it.data.title,
                        binding.collapsingToolbar,
                        binding.tvTitle,
                        binding.appbar,
                    )
                }

                is States.Error, is States.Empty -> {}
            }
        }
        collectFlow(detailMovieViewModel.cast) {
            when (it) {
                is States.Loading -> showCast(false)
                is States.Success -> {
                    showCast(true)
                    initCastAdapter(it.data)
                }

                is States.Error, is States.Empty -> {
                    binding.contentDetail.shimmerCasts.isVisible = false
                    binding.contentDetail.rvCasts.isVisible = false
                    binding.contentDetail.tvCasts.isVisible = false
                }
            }
        }
        collectFlow(detailMovieViewModel.review) {
            when (it) {
                is States.Loading -> showReview(false)
                is States.Success -> {
                    showReview(true)
                    initReviewAdapter(it.data)
                }

                is States.Error, is States.Empty -> {
                    binding.contentDetail.shimmerReview.isVisible = false
                    binding.contentDetail.rvReview.isVisible = false
                    binding.contentDetail.tvReviews.isVisible = false
                }
            }
        }
        collectFlow(detailMovieViewModel.videos) {
            when (it) {
                is States.Loading -> showVideo(false)
                is States.Success -> {
                    val data = it.data.filter { video -> video.site == "YouTube" }
                    if (data.isNotEmpty()){
                        showVideo(true)
                        initVideoAdapter(it.data)
                    } else {
                        binding.contentDetail.shimmerVideo.isVisible = data.isNotEmpty()
                        binding.contentDetail.rvVideo.isVisible = data.isNotEmpty()
                        binding.contentDetail.tvTrailer.isVisible = data.isNotEmpty()
                    }
                }

                is States.Error, is States.Empty -> {
                    binding.contentDetail.shimmerVideo.isVisible = false
                    binding.contentDetail.rvVideo.isVisible = false
                    binding.contentDetail.tvTrailer.isVisible = false
                }
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
            contentDetail.apply {
                tvDescDetail.text = data.overview
                tvReleaseDate.text = data.createDateString()
            }
            setCollapsing(
                data.title,
                collapsingToolbar,
                tvTitle, appbar
            )
        }
//        detailMovie = data
//        favorite = data.isFavorite!!
//        parentActivity.setFavoriteButton(data.isFavorite!!)
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
        }
    }

    private fun initReviewAdapter(data: List<MovieReview>) {
        val reviewAdapter = ReviewAdapter(data.takeLast(3))
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
                val intent = Intent(this@DetailMovieActivity, VideoPlayerActivity::class.java).apply {
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

fun Activity.setCollapsing(
    title: String? = null,
    collapsingToolbar: CollapsingToolbarLayout,
    tvTitle: TextView,
    appbar: AppBarLayout,
) {
    collapsingToolbar.title = ""
    tvTitle.text = " "
    collapsingToolbar.setCollapsedTitleTextColor(
        ContextCompat.getColor(
            this,
            R.color.md_theme_dark_background
        )
    )

    appbar.setExpanded(true)
    appbar.addOnOffsetChangedListener(object :
        AppBarLayout.OnOffsetChangedListener {
        var isShow = false
        var scrollRange = -1


        override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
            (appBarLayout!!.totalScrollRange - abs(n = verticalOffset).toFloat()) / appBarLayout.totalScrollRange

            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange
            }
            if (scrollRange + verticalOffset == 0) {
                collapsingToolbar.title = title
                tvTitle.text = title
                isShow = true
            } else if (isShow) {
                collapsingToolbar.title = " "
                tvTitle.text = " "
                isShow = false
            }
        }
    })

}
