package com.puzzlebench.cleanMarvelKotlin.presentation.mvp

import com.puzzlebench.cleanMarvelKotlin.data.db.RealmImpl
import com.puzzlebench.cleanMarvelKotlin.data.service.CharacterServicesImpl
import com.puzzlebench.cleanMarvelKotlin.domain.model.Character
import com.puzzlebench.cleanMarvelKotlin.domain.usecase.GetCharacterDatabaseUseCase
import com.puzzlebench.cleanMarvelKotlin.domain.usecase.GetCharacterDetailServiceUseCase
import com.puzzlebench.cleanMarvelKotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.cleanMarvelKotlin.mocks.factory.CharactersFactory
import com.puzzlebench.cleanMarvelKotlin.presentation.MainActivity
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CharacterPresenterTest {
    private var view = mock(CharacterView::class.java)
    private var characterServiceImp = mock(CharacterServicesImpl::class.java)
    private var realmImpl = mock(RealmImpl::class.java)
    @Mock
    private lateinit var activity: MainActivity
    private lateinit var characterPresenter: CharacterPresenter
    private lateinit var getCharacterServiceUseCase: GetCharacterServiceUseCase
    private lateinit var getCharacterDetailServiceUseCase: GetCharacterDetailServiceUseCase
    private lateinit var getCharacterDatabaseUseCase: GetCharacterDatabaseUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        getCharacterServiceUseCase = GetCharacterServiceUseCase(characterServiceImp)
        getCharacterDetailServiceUseCase = GetCharacterDetailServiceUseCase(characterServiceImp)
        getCharacterDatabaseUseCase = GetCharacterDatabaseUseCase(realmImpl)
        val subscriptions = mock(CompositeDisposable::class.java)
        characterPresenter = CharacterPresenter(view, getCharacterServiceUseCase, getCharacterDetailServiceUseCase, getCharacterDatabaseUseCase, subscriptions)
        Mockito.`when`(view.activity).thenReturn(activity)
    }

    @Test
    fun reposeWithError() {
        val itemsCharacters = CharactersFactory.getMockCharacter()
        val observable = Observable.just(itemsCharacters)
        Mockito.`when`(getCharacterServiceUseCase.invoke()).thenReturn(Observable.error(Exception("")))
        Mockito.`when`(getCharacterDatabaseUseCase.invoke()).thenReturn(observable)
        Mockito.`when`(view.characterPublisher).thenReturn(PublishSubject.create<Int>())
        Mockito.`when`(view.refreshPressedObservable()).thenReturn(Observable.just(true))
        characterPresenter.init()
        verify(view).init()
        verify(characterServiceImp).getCharacters()
        verify(view).hideLoadingSpinner()
        verify(view).showToastNetworkError("")
    }

    @Test
    fun reposeWithItemToShow() {
        val itemsCharacters = CharactersFactory.getMockCharacter()
        val observable = Observable.just(itemsCharacters)
        Mockito.`when`(getCharacterServiceUseCase.invoke()).thenReturn(observable)
        Mockito.`when`(getCharacterDatabaseUseCase.invoke()).thenReturn(observable)
        Mockito.`when`(view.characterPublisher).thenReturn(PublishSubject.create<Int>())
        Mockito.`when`(view.refreshPressedObservable()).thenReturn(Observable.just(true))
        characterPresenter.init()
        verify(view).init()
        verify(characterServiceImp).getCharacters()
        verify(view).hideLoadingSpinner()
        verify(view, times(2)).showCharacters(itemsCharacters)
    }

    @Test
    fun reposeWithoutItemToShow() {
        val itemsCharacters = emptyList<Character>()
        val observable = Observable.just(itemsCharacters)
        Mockito.`when`(getCharacterServiceUseCase.invoke()).thenReturn(observable)
        Mockito.`when`(getCharacterDatabaseUseCase.invoke()).thenReturn(observable)
        Mockito.`when`(view.characterPublisher).thenReturn(PublishSubject.create<Int>())
        Mockito.`when`(view.refreshPressedObservable()).thenReturn(Observable.just(true))
        characterPresenter.init()
        verify(view).init()
        verify(characterServiceImp, times(2)).getCharacters()
    }
}