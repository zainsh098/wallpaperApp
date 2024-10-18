package com.example.wallpaperapp.network

import com.example.wallpaperapp.model.PexelsResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PexelsApiService {

    // in const format
    @GET("v1/search")
    suspend fun getWallpapers(
        @Header("Authorization") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
        ): PexelsResponse
}
