package com.tiooooo.core.network.data

import com.google.gson.annotations.SerializedName

data class ApiResponseNoData(
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("messageTitle")
    val messageTitle: String?,
)
