package com.neflis.neflis.cases.video

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neflis.neflis.core.data.TokenManager
import com.neflis.neflis.core.datasource.video.VideoDatasource

class VideoViewModelFactory(
    private val context: Context
):ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoViewModel::class.java)) {
            return VideoViewModel(
                VideoDatasource(TokenManager(context)),
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}