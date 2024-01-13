package com.tiooooo.data.movie.implementation.remote.response.casts

import com.google.gson.annotations.SerializedName
import com.tiooooo.data.movie.api.model.casts.Cast


data class CastsContainerResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("cast") val cast: List<CastsResponse>?,
)

data class CastsResponse(
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("gender") val gender: Int?,
    @SerializedName("id") val id: Int?,
    @SerializedName("known_for_department") val knownForDepartment: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("original_name") val originalName: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("cast_id") val castId: Int?,
    @SerializedName("character") val character: String?,
    @SerializedName("credit_id") val creditId: String?,
    @SerializedName("order") val order: Int?,
)


fun CastsResponse?.toCast() = Cast(
    adult = this?.adult ?: false,
    gender = this?.gender ?: 0,
    id = this?.id ?: 0,
    knownForDepartment = this?.knownForDepartment.orEmpty(),
    name = this?.name.orEmpty(),
    originalName = this?.originalName.orEmpty(),
    popularity = this?.popularity ?: 0.0,
    profilePath = this?.profilePath.orEmpty(),
    castId = this?.castId ?: 0,
    character = this?.character.orEmpty(),
    creditId = this?.creditId.orEmpty(),
    order = this?.order ?: 0
)
