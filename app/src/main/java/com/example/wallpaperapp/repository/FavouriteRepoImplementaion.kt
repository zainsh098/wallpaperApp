package com.example.wallpaperapp.repository

import androidx.lifecycle.LiveData
import com.example.wallpaperapp.model.Favourite
import com.example.wallpaperapp.room.dao.FavouriteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavouriteRepoImplementation(private val favouriteDao: FavouriteDao) : FavouriteRepository {
    override suspend fun addFavourite(favourite: Favourite) {
        withContext(Dispatchers.IO) {
            favouriteDao.addFavourite(favourite)
        }
    }

    override fun getAllFavourites(): LiveData<List<Favourite>> {
        return favouriteDao.getAllFavourite()
    }
}
