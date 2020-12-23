package com.dourl.drone.coroutines

import com.dourl.drone.DataSource
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type


/**
 * creating [DataSource] from service method.
 */
class CoroutinesDataSourceCallAdapter constructor(private val responseType: Type) :
    CallAdapter<Type, Call<DataSource<Type>>> {
    override fun adapt(call: Call<Type>): Call<DataSource<Type>> {
        return DataSourceCallDelegate(call)
    }

    override fun responseType(): Type {
        return responseType
    }
}