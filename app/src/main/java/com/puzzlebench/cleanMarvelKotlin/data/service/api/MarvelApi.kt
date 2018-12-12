package com.puzzlebench.cleanMarvelKotlin.data.service.api

import com.puzzlebench.cleanMarvelKotlin.data.service.response.DataBaseResponse
import com.puzzlebench.cleanMarvelKotlin.data.service.response.MarvelBaseResponse
import retrofit2.Call
import retrofit2.http.GET

interface MarvelApi {
    @GET("/v1/public/characters")
    fun getCharacter(): Call<MarvelBaseResponse<DataBaseResponse>>
}