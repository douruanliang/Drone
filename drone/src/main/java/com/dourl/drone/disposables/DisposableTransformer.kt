package com.dourl.drone.disposables

import retrofit2.Call

fun <T> Call<T>.disposable():Disposable{
    val call = this
    return object :Disposable{
        override fun dispose() {
           if (call.isExecuted && !isDisposed()){
               call.cancel()
           }
        }

        override fun isDisposed(): Boolean = call.isCanceled

    }
}

fun <T> Call<T>.joinDisposable(compositeDisposable: CompositeDisposable)= apply {
    compositeDisposable.add(disposable())
}