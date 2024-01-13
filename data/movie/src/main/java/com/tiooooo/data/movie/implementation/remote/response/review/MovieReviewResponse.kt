package com.tiooooo.data.movie.implementation.remote.response.review

import com.google.gson.annotations.SerializedName
import com.tiooooo.data.movie.api.model.review.MovieReview

data class MovieReviewResponse(
    @SerializedName("author") val author: String?,
    @SerializedName("author_details") val authorDetails: AuthorDetailsResponse?,
    @SerializedName("content") val content: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("id") val id: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    @SerializedName("url") val url: String?,
)

data class AuthorDetailsResponse(
    @SerializedName("name")
    val name: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("avatar_path")
    val avatarPath: String?,
    @SerializedName("author")
    val rating: Int?,
)

fun MovieReviewResponse?.toMovieReview(): MovieReview =
    MovieReview(
        author = this?.author.orEmpty(),
        authorAva = this?.authorDetails?.avatarPath.orEmpty(),
        content = this?.content.orEmpty(),
        createdAt = this?.createdAt.orEmpty(),
        id = this?.id.orEmpty(),
        updatedAt = this?.updatedAt.orEmpty(),
        url = this?.url.orEmpty(),
    )
