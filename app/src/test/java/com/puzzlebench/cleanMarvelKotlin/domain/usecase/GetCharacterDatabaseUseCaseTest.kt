package com.puzzlebench.cleanMarvelKotlin.domain.usecase

import com.puzzlebench.cleanMarvelKotlin.data.db.RealmImpl
import com.puzzlebench.cleanMarvelKotlin.mocks.factory.CharactersFactory
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class GetCharacterDatabaseUseCaseTest {
    private lateinit var realmImpl: RealmImpl

    @Before
    fun setUp() {
        val items = CharactersFactory.getMockCharacter()
        val observable = Observable.just(items)
        realmImpl = mock(RealmImpl::class.java)
        `when`(realmImpl.getCharacters()).thenReturn(observable)
    }

    @Test
    operator fun invoke() {
        val getCharacterDatabaseUseCase = GetCharacterDatabaseUseCase(realmImpl)
        getCharacterDatabaseUseCase.invoke()
        verify(realmImpl).getCharacters()
    }
}