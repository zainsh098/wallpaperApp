package com.example.wallpaperapp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.wallpaperapp.model.Favourite

@Dao
interface FavouriteDao {
    @Insert
    suspend fun addFavourite(favourite: Favourite)

    @Query("SELECT * FROM favourite_table") // Replace with your actual table name
    fun getAllFavourite(): LiveData<List<Favourite>>
}
