package com.puzzlebench.cleanMarvelKotlin.domain.model

class Character(
        val id: Int,
        val name: String,
        val description: String,
        val thumbnail: Thumbnail,
        val comics: Comics,
        val urls: List<Url>
)

