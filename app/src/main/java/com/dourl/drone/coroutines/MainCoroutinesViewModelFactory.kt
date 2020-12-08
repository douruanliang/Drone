package com.dourl.drone.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dourl.drone.network.NetworkModule

class MainCoroutinesViewModelFactory:ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainCoroutinesViewModel::class.java)){
            return MainCoroutinesViewModel(NetworkModule.disneyCoroutinesService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}