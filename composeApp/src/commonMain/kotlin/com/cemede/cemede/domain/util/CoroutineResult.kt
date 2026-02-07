package com.cemede.cemede.domain.util

sealed class CoroutineResult<out T> {
    data class Success<out T>(
        val data: T,
    ) : CoroutineResult<T>()

    data class Error(
        val message: String,
        val cause: Exception? = null,
    ) : CoroutineResult<Nothing>()
}
