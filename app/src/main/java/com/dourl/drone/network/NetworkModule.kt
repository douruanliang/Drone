package com.dourl.drone.network

import com.dourl.drone.coroutines.CoroutinesDataSourceCallAdapterFactory
import com.dourl.drone.coroutines.DisneyCoroutinesService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://gist.githubusercontent.com/skydoves/aa3bbbf495b0fa91db8a9e89f34e4873/raw/a1a13d37027e8920412da5f00f6a89c5a3dbfb9a/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutinesDataSourceCallAdapterFactory())
        .build()

    // t1
    val disneyService: DisneyService = retrofit.create(DisneyService::class.java)
    // t2
    val disneyCoroutinesService: DisneyCoroutinesService =
        retrofit.create(DisneyCoroutinesService::class.java)
}