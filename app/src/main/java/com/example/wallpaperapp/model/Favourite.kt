package com.example.wallpaperapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_table") // Replace with your actual table name
data class Favourite(
    @PrimaryKey val imageUrl: String // Use the actual field name and type you need
)
