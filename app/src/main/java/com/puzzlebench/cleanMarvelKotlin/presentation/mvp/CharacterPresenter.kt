package com.puzzlebench.cleanMarvelKotlin.presentation.mvp

import com.puzzlebench.cleanMarvelKotlin.domain.model.Character
import com.puzzlebench.cleanMarvelKotlin.domain.usecase.AddToDatabaseUseCase
import com.puzzlebench.cleanMarvelKotlin.domain.usecase.GetCharacterDetailServiceUseCase
import com.puzzlebench.cleanMarvelKotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.cleanMarvelKotlin.domain.usecase.GetFromDatabaseUseCase
import com.puzzlebench.cleanMarvelKotlin.presentation.base.Presenter
import com.puzzlebench.cleanMarvelKotlin.utils.bus.RxBus
import com.puzzlebench.cleanMarvelKotlin.utils.bus.observer.OnCharacterPressedBusObserver
import com.puzzlebench.cleanMarvelKotlin.utils.bus.observer.OnRefreshPressedBusObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

const val EMPTY_STRING = ""
const val DETAIL_URL_TAG = "detail"
const val WIKI_URL_TAG = "wiki"
const val COMIC_URL_TAG = "comiclink"

open class CharacterPresenter(view: CharacterView,
                              private val getCharacterServiceUseCase: GetCharacterServiceUseCase,
                              private val getCharacterDetailServiceUseCase: GetCharacterDetailServiceUseCase,
                              private val getFromDatabaseUseCase: GetFromDatabaseUseCase,
                              private val addToDatabaseUseCase: AddToDatabaseUseCase,
                              private val subscriptions: CompositeDisposable) : Presenter<CharacterView>(view) {

    fun init() {
        view.init()
        requestGetCharacters()
        getCharacter()
        refresh()
    }

    private fun refresh() {
        RxBus.subscribe(view.activity, object : OnRefreshPressedBusObserver() {
            override fun onEvent(value: OnRefreshPressedBusObserver.OnRefreshPressed) {
                view.reset()
                val subscription = getCharacterServiceUseCase.invoke()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ characters ->
                            if (characters.isEmpty()) {
                                view.showToastNoItemToShow()
                            } else {
                                addToDatabase(characters)
                                view.showCharacters(characters)
                            }
                            view.hideLoading()
                        }, { e ->
                            view.hideLoading()
                            view.showToastNetworkError(e.message.toString())
                        })
                subscriptions.add(subscription)
            }
        })
    }

    private fun addToDatabase(list: List<Character>) {
        val subscription = addToDatabaseUseCase.invoke(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { success ->
                    if (success) {
                        view.showToastSavedToDatabase()
                    }
                }
        subscriptions.add(subscription)
    }

    private fun requestGetCharacters() {
        val subscription = getCharacterServiceUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ characters ->
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

    private fun getCharacter() {
        RxBus.subscribe(view.activity, object : OnCharacterPressedBusObserver() {
            override fun onEvent(value: OnCharacterPressedBusObserver.OnCharacterPressed) {
                val subscription = getCharacterDetailServiceUseCase.invoke(value.id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ character ->
                            view.showDialog(character.name, character.description,
                                    "${character.thumbnail.path}.${character.thumbnail.extension}",
                                    character.comics.available.toString(), character.getComicsUrl(), character.getDetailsUrl())
                        }, { e ->
                            view.showToastNetworkError(e.message.toString())
                        })
                subscriptions.add(subscription)
            }
        })
    }

}
