package com.globant.counter.utils.bus.observer

import io.reactivex.functions.Consumer

abstract class BusObserver<in T>(private val clazz: Class<T>) : Consumer<Any> {

    @Throws(Exception::class)
    override fun accept(value: Any) {
        if (value.javaClass == clazz) {
            onEvent(value as T)
        }
    }

    abstract fun onEvent(value: T)
}
