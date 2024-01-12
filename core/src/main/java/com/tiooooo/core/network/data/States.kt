package com.tiooooo.core.network.data

sealed interface States<out T> {
    object Loading : States<Nothing>

    open class Error(
        open val message: String,
        val meta: Map<String, Any?> = mapOf(),
    ) : States<Nothing>

    object Empty : States<Nothing>

    class Success<T>(
        val data: T,
        val meta: Map<String, Any?> = mapOf(),
    ) : States<T>
}
