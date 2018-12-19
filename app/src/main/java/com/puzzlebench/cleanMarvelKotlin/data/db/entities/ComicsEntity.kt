package com.puzzlebench.cleanMarvelKotlin.data.db.entities

import io.realm.RealmObject

open class ComicsEntity(
        var available: Int = 0
) : RealmObject()