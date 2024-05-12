package com.example.quanlythuchi.base

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveData<T>(value: T) : MutableLiveData<T>(value) {

    private val pending = AtomicBoolean(false)

    @MainThread
    override fun setValue(value: T?) {
        pending.set(true)
        super.setValue(value)
        val a = pending.get()
    }

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(
                SingleLiveData::class.java.simpleName,
                "Multiple observers registered but only one will be notified of changes."
            )
            return
        }
        super.observe(owner) { value ->
            val b = pending.get()
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(value)
            }
        }
    }
    fun call() {
        value = null
    }
}
