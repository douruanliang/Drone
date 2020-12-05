package com.dourl.drone

import okhttp3.Headers
import okhttp3.ResponseBody
import retrofit2.Response


/**
 * 接口相应的安全类（密封类的特性）
 */
sealed class ApiResponse<out T> {


    companion object {
        fun <T> getStatusCodeFromResponse(response: Response<T>): StatusCode {
            return StatusCode.values().find { it.code == response.code() } ?: StatusCode.Unknown
        }

        fun <T> error(ex: Throwable) = Failure.Exception<T>(ex)

        fun <T> of(
            successCodeRange: IntRange = DroneInitializer.successCodeRange,
            f: () -> Response<T>
        ): ApiResponse<T> = try {
            val response = f()
            if (response.raw().code in successCodeRange) {
                Success(response)
            } else {
                Failure.Error(response)
            }
        } catch (ex: Exception) {
            Failure.Exception(ex)
        }
    }

    data class Success<T>(val response: Response<T>) : ApiResponse<T>() {
        val statusCode: StatusCode = getStatusCodeFromResponse(response)
        val headers: Headers = response.headers()
        val raw: okhttp3.Response = response.raw()
        val data: T? = response.body()
        override fun toString(): String = "{api-response-success} (data = $data)"
    }

    sealed class Failure<T> {
        data class Error<T>(val response: Response<T>) : ApiResponse<T>() {
            val statusCode: StatusCode = getStatusCodeFromResponse(response)
            val headers: Headers = response.headers()
            val raw: okhttp3.Response = response.raw()
            val errorBody: ResponseBody? = response.errorBody()
            override fun toString(): String =
                "[api-response-fail-$statusCode](errorResponse=$response)"
        }

        data class Exception<T>(val exception: Throwable) : ApiResponse<T>() {
            val message: String? = exception.localizedMessage
            override fun toString(): String = "[api-response-fail-exception](message=$message)"
        }
    }
}