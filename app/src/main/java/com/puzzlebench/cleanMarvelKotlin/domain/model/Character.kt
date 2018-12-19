package com.puzzlebench.cleanMarvelKotlin.domain.model

import com.puzzlebench.cleanMarvelKotlin.presentation.mvp.COMIC_URL_TAG
import com.puzzlebench.cleanMarvelKotlin.presentation.mvp.DETAIL_URL_TAG
import com.puzzlebench.cleanMarvelKotlin.presentation.mvp.EMPTY_STRING
import com.puzzlebench.cleanMarvelKotlin.presentation.mvp.WIKI_URL_TAG

class Character(
        val id: Int,
        val name: String,
        val description: String,
        val thumbnail: Thumbnail,
        val comics: Comics,
        val urls: List<Url>
) {
    fun getComicsUrl(): String {
        urls.forEach {
            when (it.type) {
                COMIC_URL_TAG -> return it.url
            }
        }
        return EMPTY_STRING
    }

    fun getDetailsUrl(): String {
        urls.forEach {
            when (it.type) {
                DETAIL_URL_TAG, WIKI_URL_TAG -> return it.url
            }
        }
        return EMPTY_STRING
    }
}

