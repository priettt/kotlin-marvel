package com.puzzlebench.cleanMarvelKotlin.presentation.mvp

import com.puzzlebench.cleanMarvelKotlin.data.db.entities.CharacterEntity
import com.puzzlebench.cleanMarvelKotlin.domain.model.Character
import com.puzzlebench.cleanMarvelKotlin.domain.usecase.GetCharacterDatabaseUseCase
import com.puzzlebench.cleanMarvelKotlin.domain.usecase.GetCharacterDetailServiceUseCase
import com.puzzlebench.cleanMarvelKotlin.domain.usecase.GetCharacterServiceUseCase
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
                              private val getCharacterDatabaseUseCase: GetCharacterDatabaseUseCase,
                              private val subscriptions: CompositeDisposable) : Presenter<CharacterView>(view) {

    fun init() {
        view.init()
        retrieveFromDatabase()
        listenToRefresh()
        listenToDetails()
    }

    private fun listenToRefresh() {
        val subscriptionRefresh = view.refreshPressedObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.reset()
                    retrieveFromMarvel(true)
                }
        subscriptions.add(subscriptionRefresh)
    }

    private fun listenToDetails() {
        val subscriptionDetails = view.characterPublisher.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { id ->
                    retrieveCharacterFromMarvel(id)
                }
        subscriptions.add(subscriptionDetails)
    }

    private fun retrieveCharacterFromMarvel(id: Int) {
        val subscription = getCharacterDetailServiceUseCase.invoke(id)
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

    private fun retrieveFromDatabase() {
        subscriptions.add(getCharacterDatabaseUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { characters ->
                    if (!characters.isEmpty()) {
                        view.showCharacters(characters)
                        view.showToastLoadedFromDatabase()
                    } else retrieveFromMarvel()
                }
        )
    }

    private fun retrieveFromMarvel(toDatabase: Boolean = false) {
        subscriptions.add(getCharacterServiceUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ characters ->
                    if (characters.isEmpty()) {
                        view.showToastNoItemToShow()
                    } else {
                        view.showCharacters(characters)
                        view.showToastLoadedFromServer()
                        if (toDatabase) addFromServerToDatabase(characters)
                    }
                    view.hideLoadingSpinner()
                }, { e ->
                    view.hideLoadingSpinner()
                    view.showToastNetworkError(e.message.toString())
                })
        )
    }

    private fun addFromServerToDatabase(characters: List<Character>) {
        subscriptions.add(getCharacterDatabaseUseCase.invoke(characters)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { success ->
                    if (success) view.showToastSavedToDatabase()
                    else view.showToastErrorSavingToDatabase()
                }
        )
    }
}
