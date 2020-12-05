package com.dourl.drone

import com.dourl.drone.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback

interface DataSource<T> {

    fun combine(call: Call<T>, callBack: Callback<T>?): DataSource<T>
    fun retry(retryCount: Int, interval: Long): DataSource<T>
    fun observeResponse(observer: ResponseObserver<T>): DataSource<T>
    fun <R> concat(dataSource: DataSource<R>): DataSource<R>

    fun request(): DataSource<T>
    fun joinDisposable(disposable: CompositeDisposable): DataSource<T>
    fun invalidate()

    enum class ConcatStrategy {
        CONTINUOUS,
        BREAK
    }

}