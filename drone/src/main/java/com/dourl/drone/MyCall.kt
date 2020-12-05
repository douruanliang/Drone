package com.dourl.drone

interface MyCall<T>  {
    fun cancel()
    fun request(call: Callback<T>);
    fun clone():MyCall<T>
}