package com.dourl.drone

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type
import java.util.concurrent.Executor

class MyCallAdapter<R>(private val responseType: Type, private val executor: Executor?) :
    CallAdapter<R, MyCall<R>> {
    override fun adapt(call: Call<R>): MyCall<R> {
      return MyCallImpl(call,executor);
    }

    override fun responseType(): Type {
        return responseType
    }
}