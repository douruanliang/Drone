package com.dourl.drone.executors

import androidx.annotation.RestrictTo


@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
internal abstract class TaskExecutor {
    abstract val isMainThread: Boolean
    abstract fun executeOnDiskIO(runnable: Runnable)
    abstract fun postToMainThread(runnable: Runnable, duration: Long)


    fun executeOnMainThread(runnable: Runnable, duration: Long) {
        if (isMainThread) {
            runnable.run()
        } else {
            postToMainThread(runnable, duration)
        }
    }


}