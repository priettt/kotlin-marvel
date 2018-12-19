package com.puzzlebench.cleanMarvelKotlin.utils.bus.observer

import com.globant.counter.utils.bus.observer.BusObserver

abstract class OnRefreshPressedBusObserver : BusObserver<OnRefreshPressedBusObserver.OnRefreshPressed>
(OnRefreshPressed::class.java) {

    class OnRefreshPressed
}
