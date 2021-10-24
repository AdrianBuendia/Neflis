package com.neflis.neflis.core.datasource.mostPopularMovies

import com.neflis.neflis.core.Constants
import com.neflis.neflis.core.data.TokenManager
import com.neflis.neflis.core.models.mostPopularMovies.MostPopularMoviesResponse
import com.neflis.neflis.core.network.Api
import com.neflis.neflis.core.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MostPopularMoviesDatasource(
    private val tokenManager: TokenManager
) {
    private val apiService by lazy { Api.service }

    suspend fun getMostPopularMovies(
        page: String
    ): Resource<MostPopularMoviesResponse> {
        return try {
            val response = apiService.mostPopular(
                "${Constants.BEARER} ${tokenManager.getTokenAuthenticated()}",
                page
            )
            if (response.isSuccessful) {
                Resource.success(response.body())
            } else {
                withContext(Dispatchers.IO) {
                    //response.errorBody()?.string()?.let { Log.e("msg", "--->$it<--") }
                    //Log.e("código", response.code().toString())
                    var msg = ""
                    val errorString = response.errorBody()?.string()
                    errorString?.let {
                        val errorBody = JSONObject(errorString)
                        msg = errorBody.getString("status_message")
                    }

                    Resource.error(msg, code = response.code())
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            Resource.error("Hubo algún problema de conexión. Intentar más tarde.")
        }
    }
}