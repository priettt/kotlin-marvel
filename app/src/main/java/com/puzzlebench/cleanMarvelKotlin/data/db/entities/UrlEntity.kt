package com.puzzlebench.cleanMarvelKotlin.data.db.entities

import io.realm.RealmObject

open class UrlEntity(
        var type: String = "",
        var url: String = ""
) : RealmObject()