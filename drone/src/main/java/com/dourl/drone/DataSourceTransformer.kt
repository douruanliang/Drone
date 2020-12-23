package com.dourl.drone

fun <T> DataSource<T>.toResponseDataSource(): ResponseDataSource<T> {
    requireNotNull(this is ResponseDataSource)
    return this as ResponseDataSource<T>
}