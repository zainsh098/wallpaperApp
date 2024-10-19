package com.example.wallpaperapp.ext

import android.view.LayoutInflater
import android.view.View


val View.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(context)