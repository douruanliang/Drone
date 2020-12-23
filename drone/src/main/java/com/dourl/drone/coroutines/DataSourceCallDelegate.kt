package com.dourl.drone.coroutines

import com.dourl.drone.DataSource
import com.dourl.drone.ResponseDataSource
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class DataSourceCallDelegate<T> (proxy:Call<T>):CallDelegate<T,DataSource<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<DataSource<T>>) {
        val responseDataSource = ResponseDataSource<T>().combine(proxy,null)
        callback.onResponse(this@DataSourceCallDelegate, Response.success(responseDataSource))
    }

    override fun cloneImpl(): Call<DataSource<T>> = DataSourceCallDelegate(proxy.clone())


    override fun timeout(): Timeout = Timeout.NONE
}