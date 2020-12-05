package com.dourl.drone

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class MyCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != MyCall::class.java) return null

        check(returnType is ParameterizedType) {
            "MyCall must have generic type (e.g., MyCall<ResponseBody>)"
        }
        var responseType = getParameterUpperBound(0, returnType);
        val executor = retrofit.callbackExecutor()
        return MyCallAdapter<Any>(responseType, executor)
    }
}