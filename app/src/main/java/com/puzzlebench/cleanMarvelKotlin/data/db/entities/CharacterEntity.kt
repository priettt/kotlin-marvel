package com.puzzlebench.cleanMarvelKotlin.data.db.entities

import io.realm.RealmList
import io.realm.RealmObject

open class CharacterEntity(
        var id: Int = 0,
        var name: String = "",
        var description: String = "",
        var thumbnail: ThumbnailEntity? = ThumbnailEntity("",""),
        var comics: ComicsEntity? = ComicsEntity(0),
        var urls: RealmList<UrlEntity> = RealmList()
) : RealmObject()