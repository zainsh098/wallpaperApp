package com.example.wallpaperapp.fragments.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wallpaperapp.model.Photo
import com.example.wallpaperapp.repository.WallpaperRepository
import kotlinx.coroutines.flow.Flow

class SearchViewModel:ViewModel() {

    private val repository = WallpaperRepository()



    fun getPagedWallpapers(query: String): Flow<PagingData<Photo>> {
        return repository.getPagedWallpapers(query).cachedIn(viewModelScope)
    }
}