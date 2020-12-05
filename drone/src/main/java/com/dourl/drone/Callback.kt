package com.dourl.drone


/**
 * 最简单的方式就是用接口返回
 */
interface Callback<T> {
    fun onSuccess(data:T?);
    fun onError(error:ApiException)
}