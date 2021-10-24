package com.neflis.neflis.core.util

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val msgRes: Int? = null,
    val msg: String? = null,
    var code: Int = 0
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null, null)
        }

        fun <T> error(msg: String? = null, msgRes: Int? = null, data: T? = null, code: Int = 0): Resource<T> {
            return Resource(Status.ERROR, data, msgRes, msg, code)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}