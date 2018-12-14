package com.puzzlebench.cleanMarvelKotlin.presentation.mvp

import com.puzzlebench.cleanMarvelKotlin.domain.usecase.GetCharacterDetailServiceUseCase
import com.puzzlebench.cleanMarvelKotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.cleanMarvelKotlin.presentation.base.Presenter
import com.puzzlebench.cleanMarvelKotlin.utils.bus.RxBus
import com.puzzlebench.cleanMarvelKotlin.utils.bus.observer.OnCharacterPressedBusObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharacterPresenter(view: CharacterView,
                         private val getCharacterServiceUseCase: GetCharacterServiceUseCase,
                         private val getCharacterDetailServiceUseCase: GetCharacterDetailServiceUseCase,
                         private val subscriptions: CompositeDisposable) : Presenter<CharacterView>(view) {

    fun init() {
        view.init()
        requestGetCharacters()
        RxBus.subscribe(view.activity, object : OnCharacterPressedBusObserver() {
            override fun onEvent(value: OnCharacterPressedBusObserver.OnCharacterPressed) {
                val subscription = getCharacterDetailServiceUseCase.invoke(value.id).
                        subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ character ->
                    var detailsLink = ""
                    var comicsLink = ""
                    character.urls.forEach{
                        when (it.type) {
                            "detail", "wiki" -> detailsLink = it.url
                            "comiclink" -> comicsLink = it.url
                        }
                    }

                    view.showDialog(character.name, character.description,
                            "${character.thumbnail.path}.${character.thumbnail.extension}",
                            character.comics.available.toString(), comicsLink, detailsLink)
                }, { e ->
                    view.showToastNetworkError(e.message.toString())
                })
                subscriptions.add(subscription)
            }
        })
    }

    private fun requestGetCharacters() {
        val subscription = getCharacterServiceUseCase.invoke().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ characters ->
            if (characters.isEmpty()) {
                view.showToastNoItemToShow()
            } else {
                view.showCharacters(characters)
            }
            view.hideLoading()
        }, { e ->
            view.hideLoading()
            view.showToastNetworkError(e.message.toString())
        })
        subscriptions.add(subscription)
    }


}
