package com.example.wallpaperapp.fragments.wallpaper

import Photo
import android.util.Log
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

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getWallpapers(query: String): LiveData<List<Photo>> {
        viewModelScope.launch {
            try {
                val response = repository.getWallpapers(query)
                if (response.photos.isEmpty()) {
                    _errorMessage.value = "No wallpapers found for $query"
                } else {
                    _wallpapers.value = response.photos
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching wallpapers: ${e.localizedMessage}"
                // Log the complete error for debugging
                Log.e("WallpaperViewModel", "Error fetching wallpapers", e)
            }
        }
        return wallpapers
    }
}
