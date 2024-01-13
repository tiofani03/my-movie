package com.tiooooo.data.movie.implementation.remote.response.video

import com.google.gson.annotations.SerializedName
import com.tiooooo.data.movie.api.model.video.MovieVideo

data class MovieVideoResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("key") val key: String?,
    @SerializedName("site") val site: String?,
    @SerializedName("name") val name: String?,
)

fun MovieVideoResponse?.toMovieVideo(): MovieVideo = MovieVideo(
    id = this?.id.orEmpty(),
    key = this?.key.orEmpty(),
    site = this?.site.orEmpty(),
    name = this?.name.orEmpty(),
)
