package com.puzzlebench.cleanMarvelKotlin.data.mapper

import com.puzzlebench.cleanMarvelKotlin.data.service.response.CharacterResponse
import com.puzzlebench.cleanMarvelKotlin.data.service.response.ThumbnailResponse
import com.puzzlebench.cleanMarvelKotlin.domain.model.Character
import com.puzzlebench.cleanMarvelKotlin.domain.model.Thumbnail


open class CharacterMapperService : BaseMapperRepository<CharacterResponse, Character> {

    override fun transform(type: CharacterResponse): Character
            = Character(
            type.name,
            type.description,
            transformToThumbnail(type.thumbnail)
    )

    override fun transformToResponse(type: Character): CharacterResponse
            = CharacterResponse(
            type.name,
            type.description,
            transformToThumbnailResponse(type.thumbnail)
    )

    fun transformToThumbnail(thumbnailResponse: ThumbnailResponse): Thumbnail
            = Thumbnail(
            thumbnailResponse.path,
            thumbnailResponse.extension
    )

    fun transformToThumbnailResponse(thumbnail: Thumbnail): ThumbnailResponse
            = ThumbnailResponse(
            thumbnail.path,
            thumbnail.extension
    )

    fun transform(charactersResponse: List<CharacterResponse>): List<Character>
            = charactersResponse.map { transform(it) }

}