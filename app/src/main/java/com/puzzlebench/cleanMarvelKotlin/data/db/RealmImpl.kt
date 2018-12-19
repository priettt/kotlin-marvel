package com.puzzlebench.cleanMarvelKotlin.data.db

import com.puzzlebench.cleanMarvelKotlin.data.db.entities.CharacterEntity
import com.puzzlebench.cleanMarvelKotlin.data.mapper.CharacterMapperDatabase
import com.puzzlebench.cleanMarvelKotlin.domain.model.Character
import io.reactivex.Observable
import io.realm.Realm
import io.realm.kotlin.where

class RealmImpl(private val mapper: CharacterMapperDatabase = CharacterMapperDatabase()) {

    @Suppress("NAME_SHADOWING")
    fun addCharacters(list: List<Character>): Observable<Boolean> {
        return Observable.create { subscriber ->
            val realmInstance = Realm.getDefaultInstance()
            realmInstance.executeTransaction { realmInstance ->
                realmInstance.deleteAll()
            }
            for (item in list) {
                realmInstance.executeTransaction { realmInstance ->
                    realmInstance.insertOrUpdate(mapper.transformToResponse(item))
                }
            }
            subscriber.onNext(true)
            subscriber.onComplete()
            realmInstance.close()
        }
    }

    fun getCharacters(): Observable<List<Character>> {
        return Observable.create { subscriber ->
            val realmInstance = Realm.getDefaultInstance()
            subscriber.onNext(mapper.transform(realmInstance.where<CharacterEntity>().findAll()))
            subscriber.onComplete()
            realmInstance.close()
        }
    }
}
