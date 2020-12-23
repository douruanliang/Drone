package com.dourl.drone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dourl.drone.network.NetworkModule

class MainViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(NetworkModule.disneyService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}