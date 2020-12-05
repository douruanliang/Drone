package com.dourl.drone

import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.Executor

class MyCallImpl<T>(private val call: Call<T>, private val callbackExecutor:Executor?) : MyCall<T> {
    override fun cancel() {
        call.cancel()

    }

    override fun request(callback: Callback<T>) {
        call.enqueue(object :retrofit2.Callback<T>{
            override fun onFailure(call: Call<T>, t: Throwable) {
                fun processFailure() {
                    // 通过 transformException 函数统一处理异常
                    callback.onError(ExceptionHelper.transformException(t))
                }
                // 失败回调所执行的线程
                if (callbackExecutor != null) {
                    callbackExecutor.execute {
                        processFailure()
                    }
                } else {
                    processFailure()
                }
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {

                fun processResponse(){
                    val code = response.code()
                    if (code in 200..299){
                        callback.onSuccess(response.body())
                    }else{
                        callback.onError(ApiException(code, "$code ${response.message()}"))
                    }
                }

                if (callbackExecutor !=null) {
                    callbackExecutor.execute{
                        processResponse()
                    }
                }else{
                    processResponse()
                }


            }

        })
    }


    override fun clone(): MyCall<T> {
        return MyCallImpl(call.clone(),callbackExecutor)
    }
}