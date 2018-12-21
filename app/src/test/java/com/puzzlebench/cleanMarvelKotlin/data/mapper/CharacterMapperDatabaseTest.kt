package com.puzzlebench.cleanMarvelKotlin.data.mapper

import com.puzzlebench.cleanMarvelKotlin.data.db.entities.CharacterEntity
import com.puzzlebench.cleanMarvelKotlin.data.db.entities.ComicsEntity
import com.puzzlebench.cleanMarvelKotlin.data.db.entities.ThumbnailEntity
import com.puzzlebench.cleanMarvelKotlin.data.db.entities.UrlEntity
import com.puzzlebench.cleanMarvelKotlin.domain.model.Comics
import com.puzzlebench.cleanMarvelKotlin.domain.model.Thumbnail
import com.puzzlebench.cleanMarvelKotlin.domain.model.Url
import com.puzzlebench.cleanMarvelKotlin.domain.model.Character
import io.realm.RealmList
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CharacterMapperDatabaseTest {
    private lateinit var mapper: CharacterMapperDatabase

    @Before
    fun setUp() {
        mapper = CharacterMapperDatabase()
    }

    @Test
    fun transform() {
        val mockThumbnailResponse = ThumbnailEntity(PATH, EXTENSION)
        val mockUrlResponse = RealmList(UrlEntity(URL_TYPE, URL_LINK))
        val mockComicsResponse = ComicsEntity(COMICS_AVAILABLE)
        val mockCharacterResponse = CharacterEntity(ID, NAME, DESCRIPTION, mockThumbnailResponse, mockComicsResponse, mockUrlResponse)
        val result = mapper.transform(mockCharacterResponse)
        assertDataEquality(mockCharacterResponse, result)
    }

    @Test
    fun transformToResponse() {
        val mockThumbnail = Thumbnail(PATH, EXTENSION)
        val mockUrl = listOf(Url(URL_TYPE, URL_LINK))
        val mockComics = Comics(COMICS_AVAILABLE)
        val mockCharacter = Character(ID, NAME, DESCRIPTION, mockThumbnail, mockComics, mockUrl)
        val result = mapper.transformToResponse(mockCharacter)
        assertDataEquality(result, mockCharacter)
    }

    private fun assertDataEquality(characterResponse: CharacterEntity, character: Character) {
        Assert.assertEquals(characterResponse.name, character.name)
        Assert.assertEquals(characterResponse.description, character.description)
        Assert.assertEquals(characterResponse.thumbnail!!.path, character.thumbnail.path)
        Assert.assertEquals(characterResponse.thumbnail!!.extension, character.thumbnail.extension)
    }

}
