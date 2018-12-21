package com.puzzlebench.cleanMarvelKotlin.data.mapper

import com.puzzlebench.cleanMarvelKotlin.data.service.response.CharacterResponse
import com.puzzlebench.cleanMarvelKotlin.data.service.response.ComicsResponse
import com.puzzlebench.cleanMarvelKotlin.data.service.response.ThumbnailResponse
import com.puzzlebench.cleanMarvelKotlin.data.service.response.UrlResponse
import com.puzzlebench.cleanMarvelKotlin.domain.model.Character
import com.puzzlebench.cleanMarvelKotlin.domain.model.Comics
import com.puzzlebench.cleanMarvelKotlin.domain.model.Thumbnail
import com.puzzlebench.cleanMarvelKotlin.domain.model.Url
import junit.framework.Assert
import org.junit.Before
import org.junit.Test

const val ID = 1
const val NAME = "sport"
const val URL_TYPE = "wiki"
const val URL_LINK = "www.marvel.com"
const val COMICS_AVAILABLE = 1
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
        val mockUrlResponse = listOf(UrlResponse(URL_TYPE, URL_LINK))
        val mockComicsResponse = ComicsResponse(COMICS_AVAILABLE)
        val mockCharacterResponse = CharacterResponse(ID, NAME, DESCRIPTION, mockThumbnailResponse, mockComicsResponse, mockUrlResponse)
        val result = mapper.transform(mockCharacterResponse)
        assertBufferooDataEquality(mockCharacterResponse, result)
    }

    @Test
    fun transformToResponse() {
        val mockThumbnail = Thumbnail(PATH, EXTENSION)
        val mockUrl = listOf(Url(URL_TYPE, URL_LINK))
        val mockComics = Comics(COMICS_AVAILABLE)
        val mockCharacter = Character(ID, NAME, DESCRIPTION, mockThumbnail, mockComics, mockUrl)
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