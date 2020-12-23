package com.dourl.drone.coroutines

import com.dourl.drone.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * creating [ApiResponse] from service method.
 */
class CoroutinesResponseCallAdapter constructor(private val resultType: Type) :
    CallAdapter<Type, Call<ApiResponse<Type>>> {
    override fun adapt(call: Call<Type>): Call<ApiResponse<Type>> {
        return ApiResponseCallDelegate(call)
    }

    override fun responseType(): Type {
        return resultType
    }
}