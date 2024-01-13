package com.tiooooo.data.movie.implementation.remote.api

import com.tiooooo.core.network.data.PagingResponse
import com.tiooooo.data.movie.implementation.remote.response.casts.CastsContainerResponse
import com.tiooooo.data.movie.implementation.remote.response.detail.MovieDetailResponse
import com.tiooooo.data.movie.implementation.remote.response.genre.GenreListResponse
import com.tiooooo.data.movie.implementation.remote.response.list.MovieResultResponse
import com.tiooooo.data.movie.implementation.remote.response.review.MovieReviewResponse
import com.tiooooo.data.movie.implementation.remote.response.video.MovieVideoResponse
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

    @GET("movie/{movieId}")
    suspend fun getDetailMovie(
        @Path("movieId") movieId: String,
    ): MovieDetailResponse

    @GET("movie/{movieId}/reviews")
    suspend fun getMovieReviews(
        @Path("movieId") movieId: String,
        @Query("page") page: Int = 1,
    ): PagingResponse<MovieReviewResponse>

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCasts(
        @Path("movieId") movieId: String,
    ): CastsContainerResponse

    @GET("movie/{movieId}/videos")
    suspend fun getMovieVideo(
        @Path("movieId") movieId: String,
    ): PagingResponse<MovieVideoResponse>
}
