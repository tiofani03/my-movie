package com.tiooooo.data.movie.implementation.remote.api

import com.tiooooo.core.network.data.PagingResponse
import com.tiooooo.data.movie.implementation.remote.response.GenreListResponse
import com.tiooooo.data.movie.implementation.remote.response.MovieResultResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("genre/movie/list")
    suspend fun getGenres(
    ): GenreListResponse

    @GET("movie/{type}")
    suspend fun getMovies(
        @Path("type") type: String = "popular",
        @Query("page") page: Int = 1,
    ): PagingResponse<MovieResultResponse>

    @GET("discover/movie")
    suspend fun getDiscoverMovie(
        @Query("with_genre") genre: String = "28",
        @Query("page") page: Int = 1,
    ): PagingResponse<MovieResultResponse>
}
