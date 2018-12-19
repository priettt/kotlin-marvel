package com.puzzlebench.cleanMarvelKotlin.data.mapper

import com.puzzlebench.cleanMarvelKotlin.data.db.entities.CharacterEntity
import com.puzzlebench.cleanMarvelKotlin.data.db.entities.ComicsEntity
import com.puzzlebench.cleanMarvelKotlin.data.db.entities.ThumbnailEntity
import com.puzzlebench.cleanMarvelKotlin.data.db.entities.UrlEntity
import com.puzzlebench.cleanMarvelKotlin.domain.model.Character
import com.puzzlebench.cleanMarvelKotlin.domain.model.Comics
import com.puzzlebench.cleanMarvelKotlin.domain.model.Thumbnail
import com.puzzlebench.cleanMarvelKotlin.domain.model.Url
import io.realm.RealmList
import io.realm.RealmResults

class CharacterMapperDatabase : BaseMapperRepository<CharacterEntity, Character> {

    override fun transform(type: CharacterEntity): Character = Character(
            type.id,
            type.name,
            type.description,
            transformToThumbnail(type.thumbnail!!),
            transformToComics(type.comics!!),
            transformToUrlList(type.urls)
    )

    override fun transformToResponse(type: Character): CharacterEntity = CharacterEntity(
            type.id,
            type.name,
            type.description,
            transformToThumbnailEntity(type.thumbnail),
            transformToComicsEntity(type.comics),
            transformToUrlEntityList(type.urls)
    )

    private fun transformToThumbnail(thumbnailEntity: ThumbnailEntity): Thumbnail = Thumbnail(
            thumbnailEntity.path,
            thumbnailEntity.extension
    )

    private fun transformToThumbnailEntity(thumbnail: Thumbnail): ThumbnailEntity = ThumbnailEntity(
            thumbnail.path,
            thumbnail.extension
    )

    private fun transformToComicsEntity(comics: Comics): ComicsEntity = ComicsEntity(
            comics.available
    )

    private fun transformToComics(comicsEntity: ComicsEntity): Comics = Comics(
            comicsEntity.available
    )

    private fun transformToUrlEntityList(url: List<Url>): RealmList<UrlEntity> {
        var auxList = url.map { transformToUrlEntity(it) }
        var auxRealmList = RealmList<UrlEntity>()
        auxList.forEach { auxRealmList.add(it) }
        return auxRealmList
    }

    private fun transformToUrlList(urlEntity: List<UrlEntity>): List<Url> =
            urlEntity.map { transformToUrl(it) }

    private fun transformToUrl(urlEntity: UrlEntity): Url = Url(
            urlEntity.type,
            urlEntity.url
    )

    private fun transformToUrlEntity(url: Url): UrlEntity = UrlEntity(
            url.type,
            url.url
    )

    fun transform(dbResult: RealmResults<CharacterEntity>): List<Character> = dbResult.map { transform(it) }

}