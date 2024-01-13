package com.tiooooo.data.movie.implementation.remote.response.detail

import com.google.gson.annotations.SerializedName
import com.tiooooo.data.movie.api.model.detail.MovieDetail
import com.tiooooo.data.movie.implementation.remote.response.genre.GenreResponse

data class MovieDetailResponse(
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genres") val genres: List<GenreResponse>?,
    @SerializedName("id") val id: Int?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("video") val video: Boolean?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?,
)

fun MovieDetailResponse?.toMovieDetail() = MovieDetail(
    backdropPath = this?.backdropPath.orEmpty(),
    genres = this?.genres?.map { it.name.orEmpty() } ?: emptyList(),
    id = this?.id ?: 0,
    originalTitle = this?.originalTitle.orEmpty(),
    overview = this?.overview.orEmpty(),
    popularity = this?.popularity ?: 0.0,
    posterPath = this?.posterPath.orEmpty(),
    releaseDate = this?.releaseDate.orEmpty(),
    title = this?.title.orEmpty(),
    video = this?.video ?: false,
    voteAverage = this?.voteAverage ?: 0.0,
    voteCount = this?.voteCount ?: 0
)


