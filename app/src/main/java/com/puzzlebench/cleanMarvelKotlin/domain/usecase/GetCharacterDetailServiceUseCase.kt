package com.puzzlebench.cleanMarvelKotlin.domain.usecase

import com.puzzlebench.cleanMarvelKotlin.data.service.CharacterServicesImpl
import com.puzzlebench.cleanMarvelKotlin.domain.model.Character
import io.reactivex.Observable

open class GetCharacterDetailServiceUseCase(private val characterServiceImp: CharacterServicesImpl) {

    open operator fun invoke(id: Int): Observable<Character> = characterServiceImp.getCharacter(id)
}
