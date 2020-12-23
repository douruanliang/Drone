
@file:Suppress("unused")
package com.dourl.drone
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dourl.drone.coroutines.SuspensionFunction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@JvmSynthetic
inline fun <T> Call<T>.request(crossinline onResult: (response: ApiResponse<T>) -> Unit) = apply {
    enqueue(getCallbackFromOnResult(onResult))
}


@JvmSynthetic
fun <T> ApiResponse<T>.onSuccess(onResult: ApiResponse.Success<T>.() -> Unit): ApiResponse<T> {
    if (this is ApiResponse.Success) {
        onResult(this)
    }
    return this
}

@JvmSynthetic
fun <T> ApiResponse<T>.onFailure(onResult: ApiResponse.Failure<*>.() -> Unit): ApiResponse<T> {
    if (this is ApiResponse.Failure<*>) {
        onResult(this)
    }
    return this
}

@JvmSynthetic
fun <T> ApiResponse<T>.onException(onResult: ApiResponse.Failure.Exception<T>.() -> Unit): ApiResponse<T> {
    if (this is ApiResponse.Failure.Exception) {
        onResult(this)
    }
    return this
}
@JvmSynthetic
fun <T> ApiResponse<T>.onError(onResult: ApiResponse.Failure.Error<T>.() -> Unit):ApiResponse<T>{
    if (this is ApiResponse.Failure.Error){
        onResult(this)
    }
    return  this
}

@JvmSynthetic
@SuspensionFunction
suspend fun <T> ApiResponse<T>.suspendOnFailure(onResult: suspend ApiResponse.Failure<*>.() -> Unit): ApiResponse<T> {
    if (this is ApiResponse.Failure<*>) {
        onResult(this)
    }
    return this
}

@JvmSynthetic
@SuspensionFunction
suspend fun <T> ApiResponse<T>.suspendOnError(onResult: ApiResponse.Failure.Error<T>.() -> Unit): ApiResponse<T> {
    if (this is ApiResponse.Failure.Error) {
        onResult(this)
    }
    return this
}

@JvmSynthetic
@SuspensionFunction
suspend fun <T> ApiResponse<T>.suspendOnException(
    onResult: suspend ApiResponse.Failure.Exception<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Failure.Exception) {
        onResult(this)
    }
    return this
}


/**
 * Live data type
 */
fun <T> ApiResponse<T>.toLiveData(): LiveData<T> {
    val liveData = MutableLiveData<T>()
    if (this is ApiResponse.Success) {
        liveData.postValue(data)
    }
    return liveData
}

/** Map [ApiResponse.Failure.Error] to a customized error response model. */
fun <T, V> ApiResponse.Failure.Error<T>.map(converter: ApiErrorModelMapper<V>): V {
    return converter.map(this)
}

/** Map [ApiResponse.Failure.Error] to a customized error response model. */
@JvmSynthetic
fun <T, V> ApiResponse.Failure.Error<T>.map(
    converter: ApiErrorModelMapper<V>,
    onResult: V.() -> Unit
) {
    onResult(converter.map(this))
}

@JvmSynthetic
@PublishedApi
internal inline fun <T> getCallbackFromOnResult(
    crossinline onResult: (response: ApiResponse<T>) -> Unit
): Callback<T> {
    return object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            onResult(ApiResponse.error(t))
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            onResult(ApiResponse.of { response })
        }

    }
}

/**
 * a message from the ApiResponse failure error
 */
fun <T> ApiResponse.Failure.Error<T>.message(): String = toString()

/**
 * a message from the ApiResponse exception
 */
fun <T> ApiResponse.Failure.Exception<T>.message(): String = toString()


