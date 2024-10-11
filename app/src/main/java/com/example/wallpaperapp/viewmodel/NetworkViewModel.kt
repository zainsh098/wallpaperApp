package com.example.wallpaperapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.wallpaperapp.repository.ConnectivityRepository

class NetworkViewModel(
    private val connectivityRepository: ConnectivityRepository
) : ViewModel() {
    val isOnline = connectivityRepository.isConnected.asLiveData()
}