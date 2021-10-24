package com.neflis.neflis.core.models.mostPopularMovies

import com.google.gson.annotations.SerializedName

data class MostPopularMoviesResponse(
    val dates: DatesMostPopularMovie? = null,
    val page: Int,
    val results: List<MostPopularMovie>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int
)
