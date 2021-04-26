package com.castprogramms.karma.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> {

    data class Auth<out T : Any>(val data: T) : Result<T>()
    data class Enter<out T : Any>(val data: T) : Result<T>()

    override fun toString(): String {
        return when (this) {
            is Auth<*> -> "Success[data=$data]"
            is Enter -> "Error[exception=$data]"
        }
    }
}