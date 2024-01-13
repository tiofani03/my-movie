package com.tiooooo.mymovie.pages.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tiooooo.data.movie.api.model.MovieResult
import com.tiooooo.data.movie.api.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _movies = MutableLiveData<PagingData<MovieResult>>()
    val movies: LiveData<PagingData<MovieResult>> get() = _movies

    fun getMoviesByType(
        type: String = "popular",
    ) = viewModelScope.launch {
        movieRepository.getAllMovieByType(type).cachedIn(viewModelScope).collectLatest {
            _movies.value = it
        }
    }

}
