package com.dourl.drone

interface ResponseObserver<T>{
    fun observe(response: ApiResponse<T>)
}