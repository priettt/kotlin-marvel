package com.puzzlebench.cleanMarvelKotlin.data.db.entities

import io.realm.RealmObject

open class ThumbnailEntity(
        var path: String = "",
        var extension: String = ""
) : RealmObject()