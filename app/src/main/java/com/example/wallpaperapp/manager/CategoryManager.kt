package com.example.wallpaperapp.manager

object CategoryManager {


    private var categoryName: String? = null

    fun setCategoryName(name: String?) {
        categoryName = name
    }

    fun getCategoryName(): String? {
        return categoryName
    }


}