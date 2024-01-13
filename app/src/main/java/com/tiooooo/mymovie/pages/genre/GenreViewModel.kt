package com.tiooooo.mymovie.pages.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tiooooo.data.movie.api.model.list.MovieResult
import com.tiooooo.data.movie.api.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _movies = MutableLiveData<PagingData<MovieResult>>()
    val movies: LiveData<PagingData<MovieResult>> get() = _movies

    fun getMoviesByType(
        genreId: String = "28",
    ) = viewModelScope.launch {
        movieRepository.getDiscoverMovie(genreId).cachedIn(viewModelScope).collectLatest {
            _movies.value = it
        }
    }
}
