package com.dourl.drone.disposables


/**
 *  容器类
 */
class CompositeDisposable {
    @Volatile
    var disposed: Boolean = false
        private set

    private var disposables: MutableSet<Disposable>? = hashSetOf()

    /**
     * add new disposable
     */
    fun add(disposable: Disposable) {
        if (disposable.isDisposed()) {
            return
        }

        if (!disposed){
            synchronized(this){
                if (disposed){
                    disposables?.add(disposable)
                    return
                }
            }
        }
    }

    fun remove(disposable: Disposable){
        if (!disposed){
            synchronized(this){
                if (disposed || disposables?.remove(disposable) == false){
                    return
                }
            }
            disposable.dispose()
        }
    }

    fun clear(){
        if (!disposed){
            var mutableCollection:MutableCollection<Disposable>?
            synchronized(this){
                if (disposed){
                    return
                }
                mutableCollection = disposables
                disposables = null;
                disposed = true
            }
            mutableCollection?.forEach(Disposable::dispose)
        }
    }

}