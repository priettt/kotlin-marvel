package com.puzzlebench.cleanMarvelKotlin.data.service.response

import com.google.gson.annotations.SerializedName

class DataBaseResponse(
        @SerializedName("results") val characters: List<CharacterResponse>,
        val offset: Int,
        val total: Int
)