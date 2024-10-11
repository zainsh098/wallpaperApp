package com.example.wallpaperapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.wallpaperapp.model.PexelsResponse
import com.example.wallpaperapp.model.Photo
import com.example.wallpaperapp.network.RetrofitClient
import com.example.wallpaperapp.pagination.WallpaperPagingSource
import kotlinx.coroutines.flow.Flow

class WallpaperRepository {

    val apiService = RetrofitClient.apiService
    private val apiKey = "563492ad6f917000010000014e171b66ad0446fb8573e7d3ea7394b5"

    suspend fun getWallpapers(query: String): PexelsResponse {
        return apiService.getWallpapers(apiKey, query, 1, perPage = 15)
    }

    fun getPagedWallpapers(query: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = 15, enablePlaceholders = false),
            pagingSourceFactory = {
                WallpaperPagingSource(apiService, apiKey, query)
            }
        ).flow
    }
}
