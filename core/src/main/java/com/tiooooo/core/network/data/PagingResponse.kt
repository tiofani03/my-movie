package com.tiooooo.core.network.data

import com.google.gson.annotations.SerializedName

data class PagingResponse<T>(
    @SerializedName("currentPage", alternate = ["current_page"])
    val currentPage: Int?,
    @SerializedName("nextPage", alternate = ["next_page"])
    val nextPage: Int?,
    @SerializedName("perPage", alternate = ["per_page"])
    val perPage: Int?,
    @SerializedName("previousPage", alternate = ["previous_page"])
    val previousPage: Int?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("data")
    val data: List<T>?,
)
