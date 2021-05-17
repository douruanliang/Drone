package com.dourl.compose.observer

interface KSubject {

    fun registerObserver(observer: KObserver)
    fun removeObserver(observer: KObserver)
    fun notification()

}