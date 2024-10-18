package com.example.wallpaperapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wallpaperapp.model.Favourite
import com.example.wallpaperapp.room.dao.FavouriteDao

@Database(entities = arrayOf(Favourite::class), version = 1, exportSchema = false)

abstract class FavouriteDatabase : RoomDatabase() {

    abstract fun getFavourites(): FavouriteDao

    companion object {

        @Volatile
        private var INSTANCE: FavouriteDatabase? = null

        fun getDatabase(context: Context): FavouriteDatabase {

            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavouriteDatabase::class.java,
                    "favourite_images"
                ).build()
                INSTANCE = instance
                instance
            } }


    }


}