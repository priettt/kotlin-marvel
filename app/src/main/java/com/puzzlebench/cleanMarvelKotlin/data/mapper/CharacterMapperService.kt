package com.puzzlebench.cleanMarvelKotlin.data.mapper

import com.puzzlebench.cleanMarvelKotlin.data.service.response.CharacterResponse
import com.puzzlebench.cleanMarvelKotlin.data.service.response.ComicsResponse
import com.puzzlebench.cleanMarvelKotlin.data.service.response.ThumbnailResponse
import com.puzzlebench.cleanMarvelKotlin.data.service.response.UrlResponse
import com.puzzlebench.cleanMarvelKotlin.domain.model.Character
import com.puzzlebench.cleanMarvelKotlin.domain.model.Comics
import com.puzzlebench.cleanMarvelKotlin.domain.model.Thumbnail
import com.puzzlebench.cleanMarvelKotlin.domain.model.Url


open class CharacterMapperService : BaseMapperRepository<CharacterResponse, Character> {

    override fun transform(type: CharacterResponse): Character = Character(
            type.id,
            type.name,
            type.description,
            transformToThumbnail(type.thumbnail),
            transformToComics(type.comics),
            transformToUrlList(type.urls)
    )

    override fun transformToResponse(type: Character): CharacterResponse = CharacterResponse(
            type.id,
            type.name,
            type.description,
            transformToThumbnailResponse(type.thumbnail),
            transformToComicsResponse(type.comics),
            transformToUrlResponseList(type.urls)
    )

    private fun transformToThumbnail(thumbnailResponse: ThumbnailResponse): Thumbnail = Thumbnail(
            thumbnailResponse.path,
            thumbnailResponse.extension
    )

    private fun transformToThumbnailResponse(thumbnail: Thumbnail): ThumbnailResponse = ThumbnailResponse(
            thumbnail.path,
            thumbnail.extension
    )

    private fun transformToComicsResponse(comics: Comics): ComicsResponse = ComicsResponse(
            comics.available
    )

    private fun transformToComics(comicsResponse: ComicsResponse): Comics = Comics(
            comicsResponse.available
    )

    private fun transformToUrlResponseList(url: List<Url>): List<UrlResponse> =
            url.map { transformToUrlResponse(it) }

    private fun transformToUrlList(urlResponse: List<UrlResponse>): List<Url> =
            urlResponse.map { transformToUrl(it) }

    private fun transformToUrl(urlResponse: UrlResponse): Url = Url(
            urlResponse.type,
            urlResponse.url
    )

    private fun transformToUrlResponse(url: Url): UrlResponse = UrlResponse(
            url.type,
            url.url
    )


    fun transform(charactersResponse: List<CharacterResponse>): List<Character> = charactersResponse.map { transform(it) }

}