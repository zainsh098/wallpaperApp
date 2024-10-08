package com.example.wallpaperapp.repository

import PexelsResponse
import com.example.wallpaperapp.network.RetrofitClient

class WallpaperRepository {

    private val apiService = RetrofitClient.apiService
    private  val apiKey:String="563492ad6f917000010000014e171b66ad0446fb8573e7d3ea7394b5"
    suspend fun getWallpapers(query: String): PexelsResponse {
        return apiService.getWallpapers(
            apiKey = apiKey,
            query = query,
            per_page = 50
        )
    }
}