package com.example.wallpaperapp.fragments.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.model.Favourite
import com.example.wallpaperapp.repository.FavouriteRepository
import kotlinx.coroutines.launch

class FavouriteViewModel(private val repository: FavouriteRepository) : ViewModel() {
    fun addFavourite(favourite: Favourite) {
        viewModelScope.launch {
            repository.addFavourite(favourite)
        }
    }

    fun getAllFavourites(): LiveData<List<Favourite>> {
        return repository.getAllFavourites()
    }
}
