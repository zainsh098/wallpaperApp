package com.example.wallpaperapp.model

import androidx.room.Entity

@Entity(tableName = "favourite_table")
data class Favourite(
    val favUrl: String
)
