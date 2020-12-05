package com.dourl.drone

import retrofit2.Call

inline  fun <T> Call<T>.request(crossinline onResult:(reponse:ApiResponse<T>) -> Unit) = apply {
    //enqueue(getCallBackFromOnResult(onResult))
}