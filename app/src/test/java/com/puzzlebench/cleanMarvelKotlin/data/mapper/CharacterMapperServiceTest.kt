package com.puzzlebench.cleanMarvelKotlin.data.mapper

import com.puzzlebench.cleanMarvelKotlin.data.service.response.CharacterResponse
import com.puzzlebench.cleanMarvelKotlin.data.service.response.ThumbnailResponse
import com.puzzlebench.cleanMarvelKotlin.domain.model.Character
import com.puzzlebench.cleanMarvelKotlin.domain.model.Thumbnail
import junit.framework.Assert
import org.junit.Before
import org.junit.Test

const val NAME = "sport"
const val DESCRIPTION = "some description"
const val PATH = "http:/some.com/"
const val EXTENSION = ".PNG"

class CharacterMapperServiceTest {
    private lateinit var mapper: CharacterMapperService

    @Before
    fun setUp() {
        mapper = CharacterMapperService()

    }

    @Test
    fun transform() {

        val mockThumbnailResponse = ThumbnailResponse(PATH, EXTENSION)
        val mockCharacterResponse = CharacterResponse(NAME, DESCRIPTION, mockThumbnailResponse)
        val result = mapper.transform(mockCharacterResponse)
        assertBufferooDataEquality(mockCharacterResponse, result)
    }

    @Test
    fun transformToResponse() {
        val mockThumbnail = Thumbnail(PATH, EXTENSION)
        val mockCharacter = Character(NAME, DESCRIPTION, mockThumbnail)
        val result = mapper.transformToResponse(mockCharacter)
        assertBufferooDataEquality(result, mockCharacter)

    }

    private fun assertBufferooDataEquality(characterResponse: CharacterResponse,
                                           character: Character) {
        Assert.assertEquals(characterResponse.name, character.name)
        Assert.assertEquals(characterResponse.description, character.description)
        Assert.assertEquals(characterResponse.thumbnail.path, character.thumbnail.path)
        Assert.assertEquals(characterResponse.thumbnail.extension, character.thumbnail.extension)


    }

}