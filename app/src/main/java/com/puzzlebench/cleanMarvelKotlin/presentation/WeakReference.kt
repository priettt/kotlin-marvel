package com.puzzlebench.cleanMarvelKotlin.presentation

import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

class WeakRef<out T>(value: T) {
    private val weakReference: WeakReference<T> = WeakReference(value)
    operator fun getValue(thisRef: Any, property: KProperty<*>): T? = weakReference.get()
}