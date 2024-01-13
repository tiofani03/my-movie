package com.tiooooo.data.movie.api.repository

import androidx.paging.PagingData
import com.tiooooo.core.network.data.States
import com.tiooooo.data.movie.api.model.casts.Cast
import com.tiooooo.data.movie.api.model.detail.MovieDetail
import com.tiooooo.data.movie.api.model.list.GenreList
import com.tiooooo.data.movie.api.model.list.MovieResult
import com.tiooooo.data.movie.api.model.review.MovieReview
import com.tiooooo.data.movie.api.model.video.MovieVideo
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getGenres(): Flow<States<GenreList>>

    suspend fun getMovieByType(type: String): Flow<States<List<MovieResult>>>

    suspend fun getAllMovieByType(type: String): Flow<PagingData<MovieResult>>

    suspend fun getDiscoverMovie(genreId: String): Flow<PagingData<MovieResult>>

    suspend fun getDetailMovie(movieId: String): Flow<States<MovieDetail>>

    suspend fun getMovieReviews(movieId: String): Flow<States<List<MovieReview>>>

    suspend fun getAllMovieReviews(movieId: String): Flow<PagingData<MovieReview>>

    suspend fun getMovieCasts(movieId: String): Flow<States<List<Cast>>>

    suspend fun getMovieVideos(movieId: String): Flow<States<List<MovieVideo>>>

}
