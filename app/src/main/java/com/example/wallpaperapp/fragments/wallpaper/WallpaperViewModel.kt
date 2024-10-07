package com.example.wallpaperapp.fragments.wallpaper

import Photo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.repository.WallpaperRepository
import kotlinx.coroutines.launch

class WallpaperViewModel : ViewModel() {

    private val repository = WallpaperRepository()

    private val _wallpapers = MutableLiveData<List<Photo>>()
    val wallpapers: LiveData<List<Photo>> = _wallpapers

    fun getWallpapers(query: String): LiveData<List<Photo>> {
        viewModelScope.launch {
            val response = repository.getWallpapers(query)
            _wallpapers.value = response.photos
        }
        return wallpapers
    }
}
