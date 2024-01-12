package com.tiooooo.core.network.data

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("messageTitle")
    val messageTitle: String?,
    @SerializedName("data")
    val data: T?,
)
