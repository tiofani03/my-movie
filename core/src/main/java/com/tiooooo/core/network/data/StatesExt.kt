package com.tiooooo.core.network.data

fun <T> States<T>.handleStates(
    loadingBlock: ((Boolean) -> Unit)? = null,
    emptyBlock: (() -> Unit)? = null,
    errorBlock: ((String) -> Unit)? = null,
    successBlock: (T) -> Unit,
) {
    when (this) {
        is States.Loading -> {
            loadingBlock?.invoke(true)
        }

        is States.Success -> {
            loadingBlock?.invoke(false)
            successBlock.invoke(data)
        }

        is States.Empty -> {
            loadingBlock?.invoke(false)
            emptyBlock?.invoke()
        }

        is States.Error -> {
            loadingBlock?.invoke(false)
            val error = when (this) {
                is HttpError -> message
                is NoInternetError, is TimeOutError -> "Connection lost. Check your connection and try again."
                else -> "Ops something error, try again letter"
            }
            errorBlock?.invoke(error)
        }
    }
}
