package com.tiooooo.core.network.data

fun <T> States<T>.handleStates(
    onLoading: ((Boolean) -> Unit)? = null,
    onEmpty: (() -> Unit)? = null,
    onError: ((String) -> Unit)? = null,
    onSuccess: (T) -> Unit,
) {
    when (this) {
        is States.Loading -> {
            onLoading?.invoke(true)
        }

        is States.Success -> {
            onLoading?.invoke(false)
            onSuccess.invoke(data)
        }

        is States.Empty -> {
            onLoading?.invoke(false)
            onEmpty?.invoke()
        }

        is States.Error -> {
            onLoading?.invoke(false)
            val error = when (this) {
                is HttpError -> message
                is NoInternetError, is TimeOutError -> "Koneksi terputus. Cek sinyal kamu dan coba lagi, ya."
                else -> "Terjadi kesalahan server. Coba beberapa saat lagi, ya."
            }
            onError?.invoke(error)
        }
    }
}
