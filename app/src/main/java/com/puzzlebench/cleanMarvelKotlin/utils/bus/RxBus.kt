package com.puzzlebench.cleanMarvelKotlin.utils.bus

import com.globant.counter.utils.bus.observer.BusObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.*

object RxBus {
    private val disposableMap = HashMap<Any, CompositeDisposable>()
    private val publishSubject = PublishSubject.create<Any>()

    fun post(`object`: Any) {
        publishSubject.onNext(`object`)
    }

    fun subscribe(key: Any, busObserver: BusObserver<*>) {
        var compositeDisposable: CompositeDisposable? = disposableMap[key]
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable.add(publishSubject.subscribe(busObserver))
        disposableMap.put(key, compositeDisposable)
    }

    fun clear(key: Any) {
        val compositeDisposable = disposableMap[key]
        compositeDisposable?.clear()
        disposableMap.remove(key)
    }

    fun clearAll() {
        for ((_, value) in disposableMap) {
            value.clear()
        }
        disposableMap.clear()
    }
}