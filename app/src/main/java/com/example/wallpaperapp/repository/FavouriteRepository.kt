package com.example.wallpaperapp.repository

import androidx.lifecycle.LiveData
import com.example.wallpaperapp.model.Favourite

interface FavouriteRepository {
    suspend fun addFavourite(favourite: Favourite)
    fun getAllFavourites(): LiveData<List<Favourite>>
}
