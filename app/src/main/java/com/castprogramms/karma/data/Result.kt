package com.castprogramms.karma.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any>(val data: T? = null, val message: String? = null) {

    class Auth<out T : Any>(data: T) : Result<T>(data)
    class Enter<out T : Any>(data: T) : Result<T>(data)
    class Loading<out T: Any>(data: T? = null):Result<T>(data)
    class Fail<T: Any>(error: String?, data: T? = null,): Result<T>(data, error)

    override fun toString(): String {
        return when (this) {
            is Auth<*> -> "Success[data=$data]"
            is Enter -> "Error[exception=$data]"
            is Fail -> "Error[exception=$message]"
            is Loading ->  "Success[data=$data]"
        }
    }
}