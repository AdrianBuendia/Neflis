package com.neflis.neflis.cases.mostPopularMovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neflis.neflis.core.datasource.mostPopularMovies.MostPopularMoviesDatasource
import com.neflis.neflis.core.models.mostPopularMovies.MostPopularMovie
import com.neflis.neflis.core.models.mostPopularMovies.MostPopularMoviesResponse
import com.neflis.neflis.core.util.Event
import com.neflis.neflis.core.util.Resource
import com.neflis.neflis.core.util.Status
import kotlinx.coroutines.launch

class MostPopularMoviesViewModel(
    private val mostPopularMoviesDatasource: MostPopularMoviesDatasource
) : ViewModel() {

    private var _mostPopularMoviesResponse: MostPopularMoviesResponse? = null
    private var _mostPopularMovies: MutableList<MostPopularMovie> = mutableListOf()

    private val _mostPopularMoviesResource =
        MutableLiveData<Event<Resource<MostPopularMoviesResponse>>>()
    val mostPopularMoviesResource: LiveData<Event<Resource<MostPopularMoviesResponse>>> =
        _mostPopularMoviesResource

    fun getMostpopularMoviesResponse() = _mostPopularMoviesResponse

    fun getMostPopularMovies() = _mostPopularMovies

    fun getMostPopularMovies(page: String) {
        _mostPopularMoviesResource.value = Event(Resource(Status.LOADING, null))
        viewModelScope.launch {
            val result = mostPopularMoviesDatasource.getMostPopularMovies(
                page
            )
            _mostPopularMoviesResource.value = Event(result)
            if (result.status == Status.SUCCESS){
                _mostPopularMoviesResponse = result.data

                if(_mostPopularMovies.isNullOrEmpty()){
                    result.data?.results?.let {
                        _mostPopularMovies.addAll(
                            it
                        )
                    }
                }else {
                    _mostPopularMovies.let { list1 -> result.data?.results?.let(list1::addAll) }
                }

            }
        }
    }
}