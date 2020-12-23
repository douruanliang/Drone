package com.dourl.drone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dourl.drone.disposables.CompositeDisposable
import com.dourl.drone.disposables.disposable
import com.dourl.drone.executors.ArchTaskExecutor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResponseDataSource<T> : DataSource<T> {

    var call: Call<T>? = null

    var callback: Callback<T>? = null

    private val dataLock = Any()
    private val empty = Any()

    private var retry: Int = -1
    private var retryCount: Int = -1
        set(value) {
            if (value >= 0) {
                field = value
                retry = value
            }
        }
    private var retryTimeInterval: Long = 0
        set(value) {
            if (value >= 0) {
                field = value
            }
        }

    private val retryRunnable = Runnable {
        if (retryCount > 0) {
            synchronized(retryCount--) {
                enqueue()
            }
        }
    }

    /**
     * 数据保存策略
     */
    private var dataRetainPolicy = DataRetainPolicy.NO_RETAIN

    /***
     * 任务状态
     */
    private var compositeDisposable: CompositeDisposable? = null

    private var responseObserver: ResponseObserver<T>? = null

    private var liveData: MutableLiveData<T>? = null

    private var concat: (() -> Unit)? = null

    var concatStrategy = DataSource.ConcatStrategy.CONTINUOUS

    @Volatile
    internal var pending = empty

    @Volatile
    var data: Any = empty
        private set

    /**
     *组合调用和回调实例以缓存数据
     */
    override fun combine(call: Call<T>, callBack: Callback<T>?): DataSource<T> = apply {
        this.call = call
        this.callback = callBack
    }

    @JvmSynthetic
    inline fun combine(call: Call<T>, crossinline onResult: (response: ApiResponse<T>) -> Unit) =
        combine(call, getCallbackFromOnResult(onResult))

    /**
     * extension method for requesting and observing response at once
     */
    @JvmSynthetic
    inline fun request(crossinline action: (ApiResponse<T>).() -> Unit) = apply {
        if (call != null && callback == null) {
            combine(requireNotNull(call), action)
        }
        request()
    }



    /**
     * 请求(异步)
     */
    override fun request(): DataSource<T> = apply {
        val call = this.call ?: return this
        if (data === empty || dataRetainPolicy == DataRetainPolicy.NO_RETAIN) {
            enqueue()
        } else {
            when (val data = data as ApiResponse<T>) {
                is ApiResponse.Success<T> -> {
                    callback?.onResponse(call, data.response)
                    emitResponseToObserver()
                }
                else -> enqueue()
            }
        }
    }

    fun dataRetainPolicy(dataRetainPolicy: DataRetainPolicy) = apply {
        this.dataRetainPolicy = dataRetainPolicy
    }

    /**
     *  异步请求
     */
    private fun enqueue() {
        val call = call?.clone() ?: return
        if (!call.isExecuted && compositeDisposable?.disposed == false) {
            val callback = object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    callback?.onFailure(call, t)
                    postValue(ApiResponse.error(t))
                    ArchTaskExecutor.instance.postToMainThread(retryRunnable, retryTimeInterval)
                    call.cancel()
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    callback?.onResponse(call, response)
                    postValue(ApiResponse.of { response })
                }
            }
            compositeDisposable?.add(call.disposable())
            call.enqueue(callback)
        }
    }

    val postValueRunnable = Runnable {
        synchronized(dataLock) {
            this.data = pending
            pending = empty
            emitResponseToObserver()
        }
    }

    /**
     * 当响应成功则将数据通知出去
     */
    private fun emitResponseToObserver() {
        if (data != empty && (data as ApiResponse<T>) is ApiResponse.Success<T>) {
            this.responseObserver?.observe(data as ApiResponse<T>)
            this.liveData?.postValue((data as ApiResponse.Success<T>).data)
            this.concat?.invoke()
        } else if (concatStrategy == DataSource.ConcatStrategy.CONTINUOUS) {
            this.concat?.invoke()
        }
    }

    fun postValue(value: ApiResponse<T>) {
        val postTask: Boolean
        synchronized(dataLock) {
            postTask = pending === empty
            pending = value
        }
        if (!postTask) {
            return
        }
        ArchTaskExecutor.instance.postToMainThread(postValueRunnable, 0)
    }


    /**
     *  以LiveData输出
     */
    @Suppress("UNCHECKED_CAST")
    fun asLiveData(): LiveData<T> {
        return MutableLiveData<T>().apply {
            liveData = this
            if (data != empty) {
                val data = data as ApiResponse<T>
                if (data is ApiResponse.Success<T>) {
                    postValue(data.response.body())
                }
            }
        }
    }

    override fun retry(retryCount: Int, interval: Long) = apply {
        this.retryCount = retryCount
        this.retryTimeInterval = interval
    }

    override fun joinDisposable(disposable: CompositeDisposable) = apply {
        this.compositeDisposable = disposable
    }

    override fun invalidate() {
        this.data = empty
        this.retryCount = retry
        enqueue()
    }

    override fun observeResponse(observer: ResponseObserver<T>): DataSource<T> = apply {
        this.responseObserver = observer
    }

    @JvmSynthetic
    inline fun observerResponse(crossinline action: (ApiResponse<T>) -> Unit) =
        observeResponse(object : ResponseObserver<T> {
            override fun observe(response: ApiResponse<T>) {
                action(response)
            }

        })

    override fun <R> concat(dataSource: DataSource<R>): DataSource<R> {
        this.concat = { dataSource.request() }
        return dataSource
    }


}