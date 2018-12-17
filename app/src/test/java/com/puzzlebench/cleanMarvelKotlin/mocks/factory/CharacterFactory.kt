package com.puzzlebench.cleanMarvelKotlin.mocks.factory

import com.puzzlebench.cleanMarvelKotlin.domain.model.Character
import com.puzzlebench.cleanMarvelKotlin.domain.model.Comics
import com.puzzlebench.cleanMarvelKotlin.domain.model.Thumbnail
import com.puzzlebench.cleanMarvelKotlin.domain.model.Url


class CharactersFactory {

    companion object Factory {
        private const val BASE_ID = 1
        private const val BASE_AVAILABLE = 1
        private const val BASE_LINK = "www.marvel.com"
        private const val BASE_TYPE = "comiclink"
        private const val BASE_NAME = "Name"
        private const val BASE_DESCRIPTION = "Description"
        private const val BASE_PATH = "image"
        private const val BASE_EXTENSION = ".png"

        open fun getMockCharacter(): List<Character> {
            return listOf(1..5).map {
                Character(
                        it.step,
                        "$BASE_NAME$it",
                        "$BASE_DESCRIPTION$it",
                        Thumbnail("$BASE_PATH$it", BASE_EXTENSION),
                        Comics(BASE_AVAILABLE),
                        listOf(Url(BASE_TYPE, BASE_LINK))
                )
            }
        }
    }
}
