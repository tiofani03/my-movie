package com.tiooooo.mymovie.pages.main.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiooooo.core.network.data.States
import com.tiooooo.data.movie.api.model.GenreList
import com.tiooooo.data.movie.api.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _genres = MutableSharedFlow<States<GenreList>>()
    val genres = _genres.asSharedFlow()

    fun getGenres() {
        viewModelScope.launch {
            _genres.emitAll(movieRepository.getGenres())
        }
    }
}
