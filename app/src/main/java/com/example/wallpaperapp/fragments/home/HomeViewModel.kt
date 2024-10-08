package com.example.wallpaperapp.fragments.home
import androidx.lifecycle.ViewModel
import com.example.wallpaperapp.R
import com.example.wallpaperapp.model.Category

class HomeViewModel : ViewModel() {

    val categories = listOf(
        Category("Nature", R.drawable.nature),
        Category("Sports", R.drawable.sports),
        Category("Space", R.drawable.space),
        Category("Animals", R.drawable.animals),
        Category("Food", R.drawable.food),
        Category("Backgrounds", R.drawable.background),
        Category("Travel", R.drawable.travel)
    )
}
