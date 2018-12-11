package com.puzzlebench.cleanMarvelKotlin.mocks.factory

import com.puzzlebench.cleanMarvelKotlin.domain.model.Character
import com.puzzlebench.cleanMarvelKotlin.domain.model.Thumbnail


class CharactersFactory {

    companion object Factory {
        private const val BASE_NAME = "Name"
        private const val BASE_DESCRIPTION = "Description"
        private const val BASE_PATH = "image"
        private const val BASE_EXTENSION = ".png"

        open fun getMockCharacter(): List<Character> {
            return listOf(1..5).map {
                Character("$BASE_NAME$it", "$BASE_DESCRIPTION$it", Thumbnail("$BASE_PATH$it", BASE_EXTENSION))
            }
        }
    }
}
