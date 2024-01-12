package com.tiooooo.mymovie.pages.main.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiooooo.core.network.data.States
import com.tiooooo.data.movie.api.model.GenreList
import com.tiooooo.data.movie.api.model.MovieResult
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

    private val _nowPlaying = MutableSharedFlow<States<List<MovieResult>>>()
    val nowPlaying = _nowPlaying.asSharedFlow()

    private val _popular = MutableSharedFlow<States<List<MovieResult>>>()
    val popular = _popular.asSharedFlow()

    private val _topRated = MutableSharedFlow<States<List<MovieResult>>>()
    val topRated = _topRated.asSharedFlow()

    private val _upComing = MutableSharedFlow<States<List<MovieResult>>>()
    val upComing = _upComing.asSharedFlow()

    fun getGenres() {
        viewModelScope.launch {
            _genres.emitAll(movieRepository.getGenres())
        }
    }

    fun getNowPlaying(){
        viewModelScope.launch {
            _nowPlaying.emitAll(movieRepository.getMovieByType("now_playing"))
        }
    }

    fun getPopular(){
        viewModelScope.launch {
            _popular.emitAll(movieRepository.getMovieByType("popular"))
        }
    }

    fun getTopRated(){
        viewModelScope.launch {
            _topRated.emitAll(movieRepository.getMovieByType("top_rated"))
        }
    }

    fun getUpComing(){
        viewModelScope.launch {
            _upComing.emitAll(movieRepository.getMovieByType("upcoming"))
        }
    }
}
