package com.dourl.drone.coroutines

import com.dourl.drone.DataSource
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback

internal class DataCallDelegate<T> (proxy:Call<T>):CallDelegate<T,DataSource<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<DataSource<T>>) {
        TODO("Not yet implemented")
    }

    override fun cloneImpl(): Call<DataSource<T>> {
        TODO("Not yet implemented")
    }

    override fun timeout(): Timeout {
        TODO("Not yet implemented")
    }
}