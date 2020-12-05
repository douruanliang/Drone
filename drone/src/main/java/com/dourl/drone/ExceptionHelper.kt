package com.dourl.drone

import com.google.gson.JsonParseException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ExceptionHelper {

    companion object{
        private const val ERROR_CODE =  44444
        fun transformException(t: Throwable): ApiException {
            t.printStackTrace()
            return when (t) {
                is SocketTimeoutException -> ApiException(
                    ERROR_CODE,
                    "网络访问超时"
                )
                is ConnectException -> ApiException(
                    ERROR_CODE,
                    "网络连接异常"
                )
                is UnknownHostException -> ApiException(
                    ERROR_CODE,
                    "网络访问超时"
                )
                is JsonParseException -> ApiException(
                    ERROR_CODE,
                    "数据解析异常"
                )
                else -> ApiException(
                    ERROR_CODE,
                    t.message!!.toString()
                )
            }
        }
    }
}