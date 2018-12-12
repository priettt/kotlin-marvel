package com.puzzlebench.cleanMarvelKotlin.data.service

import com.puzzlebench.cleanMarvelKotlin.data.mapper.CharacterMapperService
import com.puzzlebench.cleanMarvelKotlin.data.service.api.MarvelApi
import com.puzzlebench.cleanMarvelKotlin.domain.model.Character
import io.reactivex.Observable


class CharacterServicesImpl(private val api: MarvelRequestGenerator = MarvelRequestGenerator(), private val mapper: CharacterMapperService = CharacterMapperService()) {


    fun getCharacters(): Observable<List<Character>> {
        return Observable.create { subscriber ->
            val callResponse = api.createService(MarvelApi::class.java).getCharacter()
            val response = callResponse.execute()

            if (response.isSuccessful) {
                subscriber.onNext(mapper.transform(response.body()!!.data!!.characters))
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}