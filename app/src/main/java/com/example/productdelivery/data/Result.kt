package com.example.productdelivery.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(
        val exception: Exception? = null,
        val message: String? = null,
        val code: Int = ErrorCode.DEFAULT.code,
        val errorId: String? = null
    ) : Result<Nothing>()

    object Loading : Result<Nothing>()

    object None : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
            None -> "None"
        }
    }
}

enum class ErrorCode(val code: Int) {
    DEFAULT(-1),
    NO_DATA_CONNECTION(1)
}
