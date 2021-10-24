package com.neflis.neflis.core.network

import com.google.gson.GsonBuilder
import com.neflis.neflis.core.Constants
import com.neflis.neflis.core.models.mostPopularMovies.MostPopularMoviesResponse
import com.neflis.neflis.core.models.video.VideoResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

private val httpClientBuilder = okhttp3.OkHttpClient.Builder()
    .connectTimeout(15, TimeUnit.SECONDS)
    .readTimeout(15, TimeUnit.SECONDS)
    .writeTimeout(15, TimeUnit.SECONDS)

private val httpClient = httpClientBuilder.build()
private val retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL_API)
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
    .client(httpClient)
    .build()

interface ApiService {

    @Headers(
        "Content-Type: application/json",
    )
    @GET("popular")
    suspend fun mostPopular(
        @Header("Authorization") token: String,
        @Query("page") page: String,
    ): Response<MostPopularMoviesResponse>

    @Headers(
        "Content-Type: application/json",
    )
    @GET("{id}/videos")
    suspend fun video(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Response<VideoResponse>
//
//
//    @Headers(
//        "Content-Type: application/json",
//    )
//    @GET("api/signos_vitales/{servicio}")
//    suspend fun vitalSigns(
//        @Header("Authorization") token: String,
//        @Path("servicio") folio: String,
//    ): Response<VitalSignsResponse>

}

object Api {
    val service: ApiService by lazy { retrofit.create(ApiService::class.java) }
}