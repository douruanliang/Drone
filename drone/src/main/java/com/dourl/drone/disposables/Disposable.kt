package com.dourl.drone.disposables

interface Disposable {

    fun dispose()
    
    fun isDisposed():Boolean
}