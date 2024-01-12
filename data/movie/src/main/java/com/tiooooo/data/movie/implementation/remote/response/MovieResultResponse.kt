package com.tiooooo.data.movie.implementation.remote.response

import com.google.gson.annotations.SerializedName
import com.tiooooo.data.movie.api.model.MovieResult

data class MovieResultResponse(
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("id") val id: Long?,
    @SerializedName("original_language") val originalLanguage: String?,
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

fun MovieResultResponse?.mapToMovieResult(): MovieResult {
    return MovieResult(
        adult = this?.adult ?: false,
        backdropPath = this?.backdropPath.orEmpty(),
        id = this?.id ?: 0,
        originalLanguage = this?.originalLanguage.orEmpty(),
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
}

