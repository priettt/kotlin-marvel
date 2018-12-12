package com.puzzlebench.cleanMarvelKotlin.domain.usecase

import com.puzzlebench.cleanMarvelKotlin.data.service.CharacterServicesImpl
import com.puzzlebench.cleanMarvelKotlin.domain.model.Character
import io.reactivex.Observable

open class GetCharacterServiceUseCase(private val characterServiceImp: CharacterServicesImpl) {

   open operator fun invoke(): Observable<List<Character>> = characterServiceImp.getCharacters()
}