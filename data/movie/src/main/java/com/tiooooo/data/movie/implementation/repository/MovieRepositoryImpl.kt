package com.tiooooo.data.movie.implementation.repository

import com.tiooooo.core.network.data.States
import com.tiooooo.core.network.data.toError
import com.tiooooo.data.movie.api.model.GenreList
import com.tiooooo.data.movie.api.model.MovieResult
import com.tiooooo.data.movie.api.repository.MovieRepository
import com.tiooooo.data.movie.implementation.remote.api.MovieApi
import com.tiooooo.data.movie.implementation.remote.response.mapToMovieResult
import com.tiooooo.data.movie.implementation.remote.response.toGenreList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val ioDispatcher: CoroutineDispatcher,
) : MovieRepository {
    override suspend fun getGenres(): Flow<States<GenreList>> {
        return flow {
            try {
                val response = movieApi.getGenres()
                emit(States.Success(data = response.toGenreList()))
            } catch (e: Exception) {
                emit(e.toError())
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getMovieByType(type: String): Flow<States<List<MovieResult>>> {
        return flow {
            try {
                val response = movieApi.getMovies(type)
                response.data?.let { list ->
                    emit(States.Success(data = list.map { it.mapToMovieResult() }))
                } ?: run {
                    emit(States.Empty)
                }
            } catch (e: Exception) {
                emit(e.toError())
            }
        }.flowOn(ioDispatcher)
    }
}
