package com.neflis.neflis.cases.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neflis.neflis.core.datasource.video.VideoDatasource
import com.neflis.neflis.core.models.video.VideoResponse
import com.neflis.neflis.core.util.Event
import com.neflis.neflis.core.util.Resource
import com.neflis.neflis.core.util.Status
import kotlinx.coroutines.launch

class VideoViewModel(
    private val videoDatasource: VideoDatasource
) : ViewModel() {
    private val _videoResource = MutableLiveData<Event<Resource<VideoResponse>>>()
    val videoResource: LiveData<Event<Resource<VideoResponse>>> =
        _videoResource

    fun getMostPopularMovies(id: String) {
        _videoResource.value = Event(Resource(Status.LOADING, null))
        viewModelScope.launch {
            val result = videoDatasource.getVideo(
                id
            )
            _videoResource.value = Event(result)
        }
    }
}