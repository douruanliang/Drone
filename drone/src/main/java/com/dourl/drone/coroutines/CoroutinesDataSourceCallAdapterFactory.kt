package com.dourl.drone.coroutines

import com.dourl.drone.DataSource
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class CoroutinesDataSourceCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                DataSource::class.java -> {
                    var returnType = getParameterUpperBound(0, callType as ParameterizedType)
                    CoroutinesDataSourceCallAdapter(returnType)
                }
                else -> null

            }
        }
        else -> null
    }


}
