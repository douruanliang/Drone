package com.dourl.drone.executors

import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RestrictTo
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
internal class DefaultTaskExecutor : TaskExecutor() {
    @Volatile
    private var mMainHandler: Handler? = null
    private val mLock = Any()
    private val mDiskIO = Executors.newFixedThreadPool(4, object : ThreadFactory {
        private val THREAD_NAME_STEM = "arch_disk_io_%d"
        private val mThreadId = AtomicInteger(0)

        override fun newThread(r: Runnable?): Thread {
            val t = Thread(r)
            t.name = String.format(THREAD_NAME_STEM, mThreadId.getAndIncrement())
            return t
        }

    })

    override val isMainThread: Boolean
        get() = TODO("Not yet implemented")

    override fun executeOnDiskIO(runnable: Runnable) {
        mDiskIO.execute(runnable)
    }

    override fun postToMainThread(runnable: Runnable, duration: Long) {
        if (mMainHandler == null) {
            synchronized(mLock) {
                if (mMainHandler == null) {
                    mMainHandler = createAsync(Looper.getMainLooper())
                }
            }
        }
        mMainHandler?.postDelayed(runnable, duration)
    }

    private fun createAsync(looper: Looper): Handler{
        if (Build.VERSION.SDK_INT >= 28) {
            return Handler.createAsync(looper)
        }
        if (Build.VERSION.SDK_INT >= 16) {
            try {
                return Handler::class.java.getDeclaredConstructor(
                    Looper::class.java,
                    Handler.Callback::class.java,
                    Boolean::class.javaPrimitiveType
                ).newInstance(looper, null, true)
            } catch (ignored: IllegalAccessException) {
            } catch (ignored: InstantiationException) {
            } catch (ignored: NoSuchMethodException) {
            } catch (e: InvocationTargetException) {
                return Handler(looper)
            }
        }
        return Handler(looper)
    }
}