package com.neflis.neflis.core.util

/**
 * Class to validate if an event (showing a toast, showing a snackbar, making visible a progress bar)
 * has been handled in the view controller within the observer
 * when LiveData has changed in a view model.
 */
open class Event<out T>(private val content: T, var source: String = "") {

    private var handled = false

    fun getContentIfNotHandled(): T? {
        return if (handled) {
            null
        } else {
            handled = true
            content
        }
    }

    fun getContent(): T = content
}