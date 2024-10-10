package com.example.wallpaperapp.fragments.wallpaper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wallpaperapp.model.Photo
import com.example.wallpaperapp.repository.WallpaperRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WallpaperViewModel : ViewModel() {

    private val repository = WallpaperRepository()

    private val _wallpapers = MutableLiveData<List<Photo>>()
    val wallpapers: LiveData<List<Photo>> = _wallpapers

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
//    val isLoading=MutableLiveData<Boolean>()


    // Function to get paginated wallpapers
    fun getPagedWallpapers(query: String): Flow<PagingData<Photo>> {
        return repository.getPagedWallpapers(query).cachedIn(viewModelScope)
    }

    // Keeping the original non-paginated function as is
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

            }
        }

        return wallpapers

    }
}
