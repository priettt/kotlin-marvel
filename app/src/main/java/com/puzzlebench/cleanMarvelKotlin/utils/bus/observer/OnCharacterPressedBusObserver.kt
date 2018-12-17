package com.puzzlebench.cleanMarvelKotlin.utils.bus.observer

import com.globant.counter.utils.bus.observer.BusObserver

abstract class OnCharacterPressedBusObserver : BusObserver<OnCharacterPressedBusObserver.OnCharacterPressed>
    (OnCharacterPressed::class.java) {

    class OnCharacterPressed(val id: Int)
}
