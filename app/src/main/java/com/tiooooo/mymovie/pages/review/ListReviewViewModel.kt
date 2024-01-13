package com.tiooooo.mymovie.pages.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tiooooo.data.movie.api.model.review.MovieReview
import com.tiooooo.data.movie.api.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListReviewViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _reviews = MutableLiveData<PagingData<MovieReview>>()
    val reviews: LiveData<PagingData<MovieReview>> get() = _reviews

    fun getMovieReview(
        movieId: String,
    ) = viewModelScope.launch {
        movieRepository.getAllMovieReviews(movieId).cachedIn(viewModelScope).collectLatest {
            _reviews.value = it
        }
    }
}
