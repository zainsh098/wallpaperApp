package com.example.wallpaperapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.wallpaperapp.model.Category

class HomeViewModel : ViewModel() {

    val categories = listOf(
        Category("nature"),
        Category("sports"),
        Category("space"),
        Category("animals"),
        Category("food"),
        Category("backgrounds"),
        Category("travel")
    )
}
