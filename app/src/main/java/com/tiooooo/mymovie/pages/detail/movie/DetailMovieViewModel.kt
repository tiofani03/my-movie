package com.tiooooo.mymovie.pages.detail.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiooooo.core.network.data.States
import com.tiooooo.data.movie.api.model.casts.Cast
import com.tiooooo.data.movie.api.model.detail.MovieDetail
import com.tiooooo.data.movie.api.model.review.MovieReview
import com.tiooooo.data.movie.api.model.video.MovieVideo
import com.tiooooo.data.movie.api.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _movie = MutableSharedFlow<States<MovieDetail>>()
    val movie = _movie.asSharedFlow()

    private val _cast = MutableSharedFlow<States<List<Cast>>>()
    val cast = _cast.asSharedFlow()

    private val _review = MutableSharedFlow<States<List<MovieReview>>>()
    val review = _review.asSharedFlow()

    private val _videos = MutableSharedFlow<States<List<MovieVideo>>>()
    val videos = _videos.asSharedFlow()

    fun getMovieDetail(movieId: String) {
        viewModelScope.launch {
            _movie.emitAll(movieRepository.getDetailMovie(movieId))
        }
    }

    fun getMovieCast(movieId: String) {
        viewModelScope.launch {
            _cast.emitAll(movieRepository.getMovieCasts(movieId))
        }
    }

    fun getMovieReview(movieId: String) {
        viewModelScope.launch {
            _review.emitAll(movieRepository.getMovieReviews(movieId))
        }
    }

    fun getMovieVideo(movieId: String) {
        viewModelScope.launch {
            _videos.emitAll(movieRepository.getMovieVideos(movieId))
        }
    }
}
