package com.example.wallpaperapp.fragments.wallpaper

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wallpaperapp.model.Photo
import com.example.wallpaperapp.repository.WallpaperRepository
import kotlinx.coroutines.flow.Flow

class WallpaperViewModel : ViewModel() {

    private val repository = WallpaperRepository()
    var recyclerViewState: Parcelable? = null

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    // Function to get paginated wallpapers
    fun getPagedWallpapers(query: String): Flow<PagingData<Photo>> {
        return repository.getPagedWallpapers(query).cachedIn(viewModelScope)
    }

}

////     Keeping the original non-paginated function as is
//    fun getWallpapers(query: String): LiveData<List<Photo>> {
//        viewModelScope.launch {
//            try {
//                val response = repository.getWallpapers(query)
//                if (response.photos.isEmpty()) {
//                    _errorMessage.value = "No wallpapers found for $query"
//                } else {
//                    _wallpapers.value = response.photos
//
//                }
//            } catch (e: Exception) {
//                _errorMessage.value = "Error fetching wallpapers: ${e.localizedMessage}"
//
//            }
//        }
//
//        return wallpapers
//
//    }


class SharedViewModel : ViewModel() {
    var selectedImageUrl: String? = null
    var selectedImageAlt: String? = null
}
