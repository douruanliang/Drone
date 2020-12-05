package com.dourl.drone.executors

import androidx.annotation.RestrictTo

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
internal class ArchTaskExecutor private constructor() : TaskExecutor() {

    private var mDelegate: TaskExecutor
    private val mDefaultTaskExecutor: TaskExecutor

    init {
        mDefaultTaskExecutor = DefaultTaskExecutor()
        mDelegate = mDefaultTaskExecutor
    }

    fun setDelegate(taskExecutor: TaskExecutor) {
        mDelegate = taskExecutor ?: mDefaultTaskExecutor
    }

    override val isMainThread: Boolean
        get() = TODO("Not yet implemented")

    override fun executeOnDiskIO(runnable: Runnable) {
        mDelegate.executeOnDiskIO(runnable)
    }

    override fun postToMainThread(runnable: Runnable, duration: Long) {
        mDelegate.postToMainThread(runnable, duration)
    }


    companion object {
        @Volatile
        private lateinit var mInstance: ArchTaskExecutor

        val instance: ArchTaskExecutor
            get() {
                if (::mInstance.isInitialized) {
                    return mInstance
                }
                synchronized(ArchTaskExecutor::class.java) {
                    if (!::mInstance.isInitialized) {
                        mInstance = ArchTaskExecutor()
                    }
                }
                return mInstance
            }
    }
}