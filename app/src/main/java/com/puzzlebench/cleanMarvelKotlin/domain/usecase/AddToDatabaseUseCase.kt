package com.puzzlebench.cleanMarvelKotlin.domain.usecase

import com.puzzlebench.cleanMarvelKotlin.data.db.RealmImpl
import com.puzzlebench.cleanMarvelKotlin.domain.model.Character
import io.reactivex.Observable

open class AddToDatabaseUseCase(private val realmImpl: RealmImpl){
    open operator fun invoke(list: List<Character>): Observable<Boolean> = realmImpl.addCharacters(list)
}