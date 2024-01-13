package com.tiooooo.data.movie.api.repository

import androidx.paging.PagingData
import com.tiooooo.core.network.data.States
import com.tiooooo.data.movie.api.model.GenreList
import com.tiooooo.data.movie.api.model.MovieResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getGenres(): Flow<States<GenreList>>

    suspend fun getMovieByType(type: String): Flow<States<List<MovieResult>>>

    suspend fun getAllMovieByType(type: String): Flow<PagingData<MovieResult>>

    suspend fun getDiscoverMovie(genreId: String): Flow<PagingData<MovieResult>>

}
