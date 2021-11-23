package com.kang.resume.base

import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.elvishew.xlog.XLog

/**
 * 类描述：
 * author:kanghuicong
 */
class EventMutableLiveData<T> : BackFillLiveData<T?>(), LifecycleObserver,
    Observer<T?> {
    var owner: LifecycleOwner? = null
    private var observer: Observer<in T>? = null

    override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
        // super.observe(owner, observer);
        observeForever(this)
        this.owner = owner
        this.observer = observer
        owner.lifecycle.addObserver(this)
    }

    /**
     * @return  是否销毁状态
     */
    private fun isDestroyed(): Boolean {
        if (owner != null) {
            if (owner!!.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                return true
            }
        }
        return false
    }

    /**
     * onDestroy 时解除各方的观察和绑定，并清空数据
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (owner != null) {
            owner!!.lifecycle.removeObserver(this)
            owner = null
        }
        removeObserver(this)
        observer = null
    }

    override fun onChanged(t: T?) {
        if (!isDestroyed() && observer != null) {
            observer!!.onChanged(t)
        }
    }

    @MainThread
    fun call() {
        setValue(null)
    }

}


open class BackFillLiveData<T> : MutableLiveData<T>() {
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
        hook(observer)
    }

    private fun hook(observer: Observer<in T>) {
        val liveDataClass = LiveData::class.java
        try {
            //获取field private SafeIterableMap<Observer<T>, ObserverWrapper> mObservers
            val mObservers = liveDataClass.getDeclaredField("mObservers")
            mObservers.isAccessible = true
            //获取SafeIterableMap集合mObservers
            val observers = mObservers[this]
            val observersClass: Class<*> = observers.javaClass
            //获取SafeIterableMap的get(Object obj)方法
            val methodGet = observersClass.getDeclaredMethod(
                "get",
                Any::class.java
            )
            methodGet.isAccessible = true
            //获取到observer在集合中对应的ObserverWrapper对象
            val objectWrapperEntry = methodGet.invoke(observers, observer)
            var objectWrapper: Any? = null
            if (objectWrapperEntry is Map.Entry<*, *>) {
                objectWrapper = objectWrapperEntry.value
            }
            if (objectWrapper == null) {
                throw NullPointerException("ObserverWrapper can not be null")
            }
            //获取ObserverWrapper的Class对象  LifecycleBoundObserver extends ObserverWrapper
            val wrapperClass: Class<*> = objectWrapper.javaClass.superclass
            //获取ObserverWrapper的field mLastVersion
            val mLastVersion = wrapperClass.getDeclaredField("mLastVersion")
            mLastVersion.isAccessible = true
            //获取liveData的field mVersion
            val mVersion = liveDataClass.getDeclaredField("mVersion")
            mVersion.isAccessible = true
            val mV = mVersion[this]
            //把当前ListData的mVersion赋值给 ObserverWrapper的field mLastVersion
            mLastVersion[objectWrapper] = mV
            mObservers.isAccessible = false
            methodGet.isAccessible = false
            mLastVersion.isAccessible = false
            mVersion.isAccessible = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
