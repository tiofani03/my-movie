package com.tiooooo.data.movie.implementation.remote.api

import com.tiooooo.data.movie.implementation.remote.response.GenreListResponse
import retrofit2.http.GET

interface MovieApi {
    @GET("genre/movie/list")
    suspend fun getGenres(
    ): GenreListResponse
}
