package com.example.wallpaperapp.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wallpaperapp.model.Photo
import com.example.wallpaperapp.network.PexelsApiService

class WallpaperPagingSource(
    private val apiService: PexelsApiService,
    private val apiKey: String,
    private val query: String
) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val currentPage = params.key ?: 1  // Start from page 1 if no key is provided
            val response = apiService.getWallpapers(apiKey, query, currentPage, params.loadSize)

            LoadResult.Page(

                data = response.photos?: emptyList(),
                prevKey = if (currentPage == 1) null else currentPage - 1, // Previous page key
                nextKey = if (response.photos.isEmpty()) null else currentPage + 1 // Next page key
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        // Return null because we don’t have any special refresh logic
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
