package com.neflis.neflis.cases.mostPopularMovies

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neflis.neflis.core.data.TokenManager
import com.neflis.neflis.core.datasource.mostPopularMovies.MostPopularMoviesDatasource

class MostPopularMoviesViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MostPopularMoviesViewModel::class.java)) {
            return MostPopularMoviesViewModel(
                MostPopularMoviesDatasource(TokenManager(context)),
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}