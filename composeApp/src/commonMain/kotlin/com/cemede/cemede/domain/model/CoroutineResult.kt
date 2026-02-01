package com.cemede.cemede.domain.model

sealed class CoroutineResult<out T> {
    data class Success<out T>(
        val data: T,
    ) : CoroutineResult<T>()

    data class Error(
        val message: String,
        val cause: Exception? = null,
    ) : CoroutineResult<Nothing>()
}
